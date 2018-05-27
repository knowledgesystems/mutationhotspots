/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotDetectiveParameters;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotException;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat.DecoySignificanceTest;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat.DetectedInDecoy;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat.StructureHotspotDetectedInDecoy;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.ContactMap;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure.ContactMapFromPDB;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure.ProteinStructureContactMapCalculator;


/**
 *
 * @author jgao
 */
public class ProteinStructureHotspotDetective extends AbstractHotspotDetective {
    
    public ProteinStructureHotspotDetective(HotspotDetectiveParameters parameters) {
        super(parameters);
    }
    
    /**
     *
     * @param hotspots
     * @return
     */
    @Override
    protected Set<Hotspot> processSingleHotspotsOnAProtein(MutatedProtein protein,
            Map<Integer, Hotspot> mapResidueHotspot) throws HotspotException {
        if (protein.getLength()>5000) {
            System.out.println("Protein longer than 5000, skipping..");
            return Collections.emptySet();
        }
        
        int[] counts = getMutationCountsOnProtein(mapResidueHotspot, protein.getLength());
        
        Map<SortedSet<Integer>,Set<Hotspot>> mapResiduesHotspots3D = new HashMap<>();
        Map<MutatedProtein3D,ContactMap> contactMaps = ProteinStructureContactMapCalculator.getPDBCalculator().getContactMaps(protein,
                parameters.getIdentpThresholdFor3DHotspots(), parameters.getDistanceClosestAtomsThresholdFor3DHotspots());
        contactMaps.putAll(ProteinStructureContactMapCalculator.getSwissModelCalculator().getContactMaps(protein,
                parameters.getIdentpThresholdFor3DHotspots(), parameters.getDistanceClosestAtomsThresholdFor3DHotspots()));
        
        int i = 0;
        for (Map.Entry<MutatedProtein3D, ContactMap> entryContactMaps : contactMaps.entrySet()) {
            MutatedProtein3D protein3D = entryContactMaps.getKey();
            ContactMap contactMap = entryContactMaps.getValue();
            
            if (contactMap.getProteinRight()>protein.getLength()) {
                System.err.println("\tMapped Protein residue longer than protein length.");
                continue;
            }
            
            DecoySignificanceTest decoySignificanceTest = null;
            
            if (parameters.calculatePValue()) {
                decoySignificanceTest = new DecoySignificanceTest(counts, contactMap.getProteinLeft(), contactMap.getProteinRight()+1);
            }
            
            System.out.println("\t"+protein3D.getGeneSymbol()+" "+(++i)+"/"+contactMaps.size()+". Processing "
                    +protein3D.getPdbId()+"."+protein3D.getPdbChain());
            
            Set<SortedSet<Integer>> clusters = findConnectedNeighbors(contactMap.getContact(), mapResidueHotspot.keySet());
            for (SortedSet<Integer> residues : clusters) {
                if (residues.size()<=1) {
                    continue;
                }
                
                Hotspot hotspot3D = new HotspotImpl(protein3D, residues);
                
                int maxCap = 0;
                for (int residue : residues) {
                    Hotspot hotspot = mapResidueHotspot.get(residue);
                    hotspot3D.mergeHotspot(hotspot);
                    
                    int num = hotspot.getPatients().size();
                    if (num > maxCap) {
                        maxCap = num;
                    }
                }
                
                if (hotspot3D.getPatients().size()>=parameters.getThresholdSamples()) {
                    Set<Hotspot> hotspots3D = mapResiduesHotspots3D.get(residues);
                    if (hotspots3D==null) {
                        hotspots3D = new HashSet<Hotspot>();
                        mapResiduesHotspots3D.put(residues, hotspots3D);
                    }
                    
                    if (parameters.calculatePValue()) {
//                        DetectedInDecoy detectedInDecoy = new PositionsDetectedInDecoy(hotspot3D.getPatients().size(), residues);
                        DetectedInDecoy detectedInDecoy = new StructureHotspotDetectedInDecoy(contactMap, maxCap, hotspot3D.getPatients().size());
                        double p = decoySignificanceTest.test(detectedInDecoy);
                        hotspot3D.setPValue(p);
                    }
                    
                    hotspots3D.add(hotspot3D);
                    
                }
            }
        }
        
        if (mapResiduesHotspots3D.isEmpty()) {
            return Collections.emptySet();
        }
        
        Set<Hotspot> hotspots3D = new HashSet<>(); 
        mapResiduesHotspots3D.forEach((residues, hotspots)->{
            Hotspot3D hotspot3D = new Hotspot3DImpl(protein, residues, hotspots);
            hotspot3D.mergeHotspot(hotspots.iterator().next()); // add mutations
            hotspots3D.add(hotspot3D);
        });
        
        return hotspots3D;
    }
    
    protected int[] getMutationCountsOnProtein(Map<Integer, Hotspot> mapResidueHotspot, int len) {
        int[] ret = new int[len+1];
        for (Map.Entry<Integer, Hotspot> entry : mapResidueHotspot.entrySet()) {
            ret[entry.getKey()] = entry.getValue().getPatients().size();
        }
        return ret;//Arrays.asList(ArrayUtils.toObject(ret));
    }
    
    private Set<SortedSet<Integer>> findConnectedNeighbors(boolean[][] graph, Set<Integer> nodes) {
        Set<SortedSet<Integer>> ret = new HashSet<SortedSet<Integer>>(nodes.size());
        for (Integer node : nodes) {
            SortedSet<Integer> set = new TreeSet<Integer>();
            set.add(node);
            for (Integer neighbor : nodes) {
                if (graph[node][neighbor]) {
                    set.add(neighbor);
                }
            }
            ret.add(set);
        }
        return ret;
    }
    
    private List<SortedSet<Integer>> cleanResiduesSet(Collection<SortedSet<Integer>> residuesSet) {
        List<SortedSet<Integer>> ret = new ArrayList<SortedSet<Integer>>();
        for (SortedSet<Integer> residues : residuesSet) {
            boolean exist = false;
            for (SortedSet<Integer> existing : ret) {
                if (existing.containsAll(residues)) {
                    exist = true;
                    break;
                }
                
                if (residues.containsAll(existing)) {
                    existing.addAll(residues);
                    exist = true;
                    break;
                }
            }
            
            if (!exist) {
                ret.add(residues);
            }
        }
        return ret;
    }
}
