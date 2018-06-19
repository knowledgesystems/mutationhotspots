/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure;

import com.fasterxml.jackson.databind.JsonNode;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.ContactMap;
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
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.contact.AtomContact;
import org.biojava.nbio.structure.contact.AtomContactSet;
import org.biojava.nbio.structure.contact.Pair;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProtein3DImpl;
import org.genomenexus.g2s.client.model.Alignment;
import org.genomenexus.g2s.client.model.ResidueMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;

/**
 *
 * @author jgao
 */
public class ContactMapFromSwissModel implements ProteinStructureContactMapCalculator { 
    
    ContactMapFromSwissModel() {
        
    }
    
    @Override
    public Map<MutatedProtein3D, ContactMap> getContactMaps(MutatedProtein mutatedProtein, 
            double identpThreshold, double distanceThresholdClosestAtoms) {
        Map<MutatedProtein3D, ContactMap> contactMaps = new HashMap<>();
        
        String uniprotAcc = mutatedProtein.getUniprotAcc();
        if (uniprotAcc == null) {
            return Collections.emptyMap();
        }
        
        List<Map<String, Object>> structures = readSwissModel(uniprotAcc);
        for (Map<String, Object> structure : structures) {
            Object obj = structure.get("identity");
            if (obj != null) {
                double identity = Double.class.cast(obj);
                if (identity < identpThreshold) {
                    continue;
                }
            } // if identity not available, still process
            
            obj = structure.get("template");
            if (obj == null) {
                continue;
            }
            String[] template = String.class.cast(obj).split("\\.");
            String pdbId = template[0];
            String chain = template[2];
            
            obj = structure.get("coordinates");
            if (obj == null) {
                continue;
            }
            String structureUrl = String.class.cast(obj);
            
            obj = structure.get("from");
            if (obj == null) {
                continue;
            }
            int from = Integer.class.cast(obj);
            
            obj = structure.get("to");
            if (obj == null) {
                continue;
            }
            int to = Integer.class.cast(obj);
            
            MutatedProtein3DImpl protein3D = new MutatedProtein3DImpl(mutatedProtein);
            protein3D.setPdbId("swissmodel:"+pdbId);
            protein3D.setPdbChain(chain);
            
            Map<Integer, Set<Integer>> pdbContactMap = getPdbContactMap(structureUrl, distanceThresholdClosestAtoms);
            
            ContactMap contactMap = getContactMap(pdbContactMap, from, to, mutatedProtein.getLength());
            
            contactMaps.put(protein3D, contactMap);
        }

        return contactMaps;
    }
    
    private List<Map<String, Object>> readSwissModel(String uniprotAcc) {
        List<Map<String, Object>> ret = new ArrayList<>();
        
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root;
        try {
            URL url = new URL("https://swissmodel.expasy.org/repository/uniprot/"+uniprotAcc+".json?provider=swissmodel");
            root = mapper.readTree(url);
        } catch (IOException ex) {
            Logger.getLogger(ContactMapFromSwissModel.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList();
        }
        
        JsonNode structuresNode = root.get("result").get("structures");
        Iterator<JsonNode> it = structuresNode.elements();
        while (it.hasNext()) {
            JsonNode strucNode = it.next();
            Map<String, Object> map = mapper.convertValue(strucNode, Map.class);
            ret.add(map);
        }
        
        return ret;
    }
    
    private ContactMap getContactMap(Map<Integer, Set<Integer>> pdbContactMap,
            int start, int end, int proteinLength) {
        ContactMap contactMap = new ContactMap(proteinLength);
        contactMap.setProteinLeft(start);
        contactMap.setProteinRight(end);
        
        boolean[][] matrix = contactMap.getContact();
        
        for (Map.Entry<Integer, Set<Integer>> entryPdbContactMap : pdbContactMap.entrySet()) {
            int pdbPos = entryPdbContactMap.getKey();
            
            Set<Integer> pdbNeighbors = entryPdbContactMap.getValue();
            if (pdbNeighbors.isEmpty()) {
                continue;
            }

            for (Integer pdbNeighbor : pdbNeighbors) {
                matrix[pdbPos][pdbNeighbor] = true;
                matrix[pdbNeighbor][pdbPos] = true;
            }
        }
        
        return contactMap;
    }
    
    private Map<Integer, Set<Integer>> getPdbContactMap(String strUrl, double distanceThreashold) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        try {
            PDBFileReader reader = new PDBFileReader();
            URL url = new URL(strUrl);
            Structure structure = reader.getStructure(url.openStream());
//            FileInputStream fis = new FileInputStream("/Users/jgao/projects/mutationhotspots/process/akt/3o96.pdb");
//            Structure structure = reader.getStructure(fis);

            Chain chain = structure.getChainByIndex(0);
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
            e.printStackTrace();
            System.err.println("Unable to calculate contact map for "+strUrl);
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
        
        for (Alignment alignment : alignments) {
            List<ResidueMapping> residueMapping = alignment.getResidueMapping();
            
            for (ResidueMapping r : residueMapping) {
                if (!r.getQueryAminoAcid().equals(r.getPdbAminoAcid())) {
                    continue;
                }
                
                Integer iseq = r.getQueryPosition();
                Integer ipdb = r.getPdbPosition();
                
                map.put(ipdb, iseq);
            }
        }
        return map;
    }
}
