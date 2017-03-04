/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.contact.AtomContact;
import org.biojava.nbio.structure.contact.AtomContactSet;
import org.biojava.nbio.structure.contact.Pair;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProtein3DImpl;
import org.genomenexus.g2s.client.ApiException;
import org.genomenexus.g2s.client.api.AlignmentApi;
import org.genomenexus.g2s.client.api.UniprotApi;
import org.genomenexus.g2s.client.model.Alignment;
import org.genomenexus.g2s.client.model.ResiduePresent;

/**
 *
 * @author jgao
 */
public final class ProteinStructureUtils {
    private static final ProteinStructureUtils instance = new ProteinStructureUtils();
    public static ProteinStructureUtils getInstance() {
        return instance;
    }
    
    private final UniprotApi g2sUniprotApi;
    private final AlignmentApi g2sAlignmentApi;

    private ProteinStructureUtils() {        
        AtomCache atomCache = new AtomCache();
//        if (dirPdbCache!=null) {
//            atomCache.setCachePath(dirPdbCache);
//        }
        FileParsingParameters params = new FileParsingParameters();
        params.setAlignSeqRes(true);
        params.setParseSecStruc(false);
        atomCache.setFileParsingParams(params);
        StructureIO.setAtomCache(atomCache);
        
        g2sUniprotApi = new UniprotApi();
        g2sAlignmentApi = new AlignmentApi();
    }
    
    public Map<MutatedProtein3D, ContactMap> getContactMaps(MutatedProtein mutatedProtein, 
            double identpThreshold, double distanceThresholdClosestAtoms) {
        Map<MutatedProtein3D, ContactMap> contactMaps = new HashMap<>();
        Map<MutatedProtein3D, List<Alignment>> mapAlignments = getAlignments(mutatedProtein, identpThreshold);
        mapAlignments.forEach((protein3D, alignments)->{
            System.out.println("calculating contact map for "+protein3D.getPdbId()+"."+protein3D.getPdbChain());
            OneToOneMap<Integer, Integer> pdbSeqResidueMapping = getPdbSeqResidueMapping(alignments);
            Map<Integer, Set<Integer>> pdbContactMap = getPdbContactMap(protein3D.getPdbId(), protein3D.getPdbChain(), distanceThresholdClosestAtoms);
            ContactMap contactMap = getContactMap(pdbContactMap, pdbSeqResidueMapping, mutatedProtein.getProteinLength());
            contactMaps.put(protein3D, contactMap);
        });
        return contactMaps;
    }
    
    private ContactMap getContactMap(Map<Integer, Set<Integer>> pdbContactMap,
            OneToOneMap<Integer, Integer> pdbSeqResidueMapping, int proteinLength) {
        ContactMap contactMap = new ContactMap(proteinLength);
        contactMap.setProteinLeft(pdbSeqResidueMapping.getSmallestValue());
        contactMap.setProteinRight(pdbSeqResidueMapping.getLargestValue());
        
        boolean[][] matrix = contactMap.getContact();
        
        for (Map.Entry<Integer, Set<Integer>> entryPdbContactMap : pdbContactMap.entrySet()) {
            int pdbPos = entryPdbContactMap.getKey();
            if (!pdbSeqResidueMapping.hasKey(pdbPos)) {
                continue;
            }
            
            int uniprotPos = pdbSeqResidueMapping.getByKey(pdbPos);
            if (uniprotPos > proteinLength) {
                System.err.println("Mapped Protein resisue longer than protein length");
                continue;
            }
            Set<Integer> pdbNeighbors = entryPdbContactMap.getValue();
            if (pdbNeighbors.isEmpty()) {
                continue;
            }

            for (Integer pdbNeighbor : pdbNeighbors) {
                if (!pdbSeqResidueMapping.hasKey(pdbNeighbor)) {
                    continue;
                }
                int uniprotNeighbor = pdbSeqResidueMapping.getByKey(pdbNeighbor);
                if (uniprotNeighbor > proteinLength) {
                    System.err.println("Mapped Protein resisue longer than protein length");
                    continue;
                }
                matrix[uniprotPos][uniprotNeighbor] = true;
                matrix[uniprotNeighbor][uniprotPos] = true;
            }
        }
        
        return contactMap;
    }
    
    private Map<Integer, Set<Integer>> getPdbContactMap(String pdbId, String chainId, double distanceThreashold) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        try {
            Structure structure = StructureIO.getStructure(pdbId);
            Chain chain = structure.getChainByPDB(chainId);
            AtomContactSet contacts = StructureTools.getAtomsInContact(chain, distanceThreashold);
            
            Iterator<AtomContact> it = contacts.iterator();
            while (it.hasNext()) {
                AtomContact atomContact = it.next();
                Pair<Atom> atoms = atomContact.getPair();
                Group group1 = atoms.getFirst().getGroup();
                Group group2 = atoms.getSecond().getGroup();
                if (!group1.hasAminoAtoms() || !group2.hasAminoAtoms() || group1==group2) {
                    continue;
                }
                
                Integer res1 = group1.getResidueNumber().getSeqNum();
                Integer res2 = group2.getResidueNumber().getSeqNum();
                
                Set<Integer> neighbors = map.get(res1);
                if (neighbors == null) {
                    neighbors = new HashSet<>();
                    map.put(res1, neighbors);
                }
                
                neighbors.add(res2);
                
                // we don't add res1 to the neighbors of res2, so it's not a complete map
            }
        } catch (Exception e) {
            System.err.println("Unable to calculate contact map for "+pdbId+"."+chainId);
        }
        
        return map;
    }
     
    
    private Map<MutatedProtein3D, List<Alignment>> getAlignments(MutatedProtein mutatedProtein, double identpThreshold) {
        Map<MutatedProtein3D, List<Alignment>> map = new HashMap<>();
        
        try {
            List<Alignment> alignments = g2sUniprotApi.getPdbAlignmentByUniprotIdUsingGET1(mutatedProtein.getUniprotAcc());
            
            alignments.forEach((alignment) -> {
                double identp = 100.0 * alignment.getIdentity() / (alignment.getSeqTo() - alignment.getSeqFrom() + 1);
                if (identp >= identpThreshold) {
                    MutatedProtein3DImpl protein3D = new MutatedProtein3DImpl(mutatedProtein);
                    protein3D.setPdbId(alignment.getPdbId());
                    protein3D.setPdbChain(alignment.getChain());

                    List<Alignment> list = map.get(protein3D);
                    if (list==null) {
                        list = new ArrayList<>();
                        map.put(protein3D, list);
                    }
                    list.add(alignment);
                }
            });
            
        } catch (ApiException ex) {
            System.err.println("Could not retrieve alignments for "+mutatedProtein.getUniprotAcc());
            System.err.println(ex.getMessage());
        }
        
        return map;
    }
    
    private OneToOneMap<Integer, Integer> getPdbSeqResidueMapping(List<Alignment> alignments) {
        Collections.sort(alignments, (Alignment align1, Alignment align2) -> {
            int ret = align1.getEvalue().compareTo(align2.getEvalue()); // sort from small evalue to large evalue
            if (ret == 0) {
                ret = -align1.getIdentity().compareTo(align2.getIdentity());
            }
            return ret;
        });
        
        OneToOneMap<Integer, Integer> map = new OneToOneMap<Integer, Integer>();
        
        for (Alignment a : alignments) {
            List<ResiduePresent> residues;
            try {
                residues = g2sAlignmentApi.getResidueMappingByAlignmentIdUsingGET(a.getAlignmentId());
            } catch (ApiException ex) {
                residues = Collections.emptyList();
                System.err.println("couldn't get residue mapping for alignment "+a.getAlignmentId());
            }
            
            for (ResiduePresent r : residues) {
                if (!r.getInputName().equals(r.getResidueName())) {
                    continue;
                }
                
                Integer iseq = r.getInputNum();
                Integer ipdb = r.getResidueNum();
                
                map.put(ipdb, iseq);
            }
        }
        return map;
    }
}
