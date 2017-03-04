/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotDetectiveParameters;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotException;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.ContactMap;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.ProteinStructureUtils;


/**
 *
 * @author jgao
 */
public class ProteinStructureHotspotDetective extends AbstractHotspotDetective {
    
    public ProteinStructureHotspotDetective(HotspotDetectiveParameters parameters) throws HotspotException {
        super(parameters);
    }
    
    /**
     *
     * @param hotspots
     * @return
     */
    @Override
    protected Map<MutatedProtein,Set<Hotspot>> processSingleHotspotsOnAProtein(MutatedProtein protein,
            Map<Integer, Hotspot> mapResidueHotspot) throws HotspotException {
        if (protein.getProteinLength()>5000) {
            System.out.println("Protein longer than 5000, skipping..");
            return Collections.emptyMap();
        }
        
        int[] counts = getMutationCountsOnProtein(mapResidueHotspot, protein.getProteinLength());
        
        Map<SortedSet<Integer>,Set<Hotspot>> mapResiduesHotspots3D = new HashMap<>();
        Map<MutatedProtein3D,ContactMap> contactMaps = ProteinStructureUtils.getInstance().getContactMaps(protein,
                parameters.getIdentpThresholdFor3DHotspots(), parameters.getDistanceClosestAtomsThresholdFor3DHotspots());
        int i = 0;
        for (Map.Entry<MutatedProtein3D, ContactMap> entryContactMaps : contactMaps.entrySet()) {
            MutatedProtein3D protein3D = entryContactMaps.getKey();
            ContactMap contactMap = entryContactMaps.getValue();
            
            if (contactMap.getProteinRight()>protein.getProteinLength()) {
                System.err.println("\tMapped Protein resisue longer than protein length.");
                continue;
            }
            
            int[][] decoyCountsList = null;
            
            if (parameters.calculatePValue()) {
                decoyCountsList = generateDecoys(counts, contactMap.getProteinLeft(), contactMap.getProteinRight()+1, 10000);
            }
            
            System.out.println("\t"+protein3D.getGene()+" "+(++i)+"/"+contactMaps.size()+". Processing "
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
                        DetectedInDecoy detectedInDecoy = new StructureHotspotDetectedInDecoy(contactMap, maxCap, hotspot3D.getPatients().size());
                        double p = getP(detectedInDecoy, decoyCountsList);
                        hotspot3D.setPValue(p);
                    }
                    
                    hotspots3D.add(hotspot3D);
                    
                }
            }
        }
        
        if (mapResiduesHotspots3D.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Set<Hotspot> hotspots3D = new HashSet<Hotspot>(); 
        for (Map.Entry<SortedSet<Integer>,Set<Hotspot>> entryMapResiduesHotspots3D : mapResiduesHotspots3D.entrySet()) {
            SortedSet<Integer> residues = entryMapResiduesHotspots3D.getKey();
            Set<Hotspot> hotspots = entryMapResiduesHotspots3D.getValue();
            Hotspot3D hotspot3D = new Hotspot3DImpl(protein, residues, hotspots);
            hotspot3D.mergeHotspot(hotspots.iterator().next()); // add mutations
            hotspots3D.add(hotspot3D);
        }
        
        return Collections.singletonMap(protein, hotspots3D);
    }
    
    protected int[] getMutationCountsOnProtein(Map<Integer, Hotspot> mapResidueHotspot, int len) {
        int[] ret = new int[len+1];
        for (Map.Entry<Integer, Hotspot> entry : mapResidueHotspot.entrySet()) {
            ret[entry.getKey()] = entry.getValue().getPatients().size();
        }
        return ret;//Arrays.asList(ArrayUtils.toObject(ret));
    }
    
    protected int[][] generateDecoys(int[] counts, int left, int right, int times) {
        int[][] decoys = new int[times][];
        for (int i=0; i<times; i++) {
            decoys[i] = Arrays.copyOf(counts, counts.length);
            shuffleArray(decoys[i], left, right);
        }
        return decoys;
    }
    
    public static void shuffleArray(int[] a, int left, int right) {
        Random random = new Random();
        random.nextInt();
        for (int i = left; i < right; i++) {
          int change = i + random.nextInt(right - i);
          swap(a, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
    
    static interface DetectedInDecoy {
        boolean isDetectedInDecoy(final int[] decoy);
    }
    
    private static class StructureHotspotDetectedInDecoy implements DetectedInDecoy {
        private final ContactMap contactMap;
        private final int maxCap;
        private final int targetCount;
        StructureHotspotDetectedInDecoy(final ContactMap contactMap, final int maxCap, final int targetCount) {
            this.contactMap = contactMap;
            this.maxCap = maxCap;
            this.targetCount = targetCount;
        }
        public boolean isDetectedInDecoy(final int[] decoy) {
            boolean[][] graph = contactMap.getContact();
            int l = contactMap.getProteinLeft();
            int r = contactMap.getProteinRight();
            for (int i=l; i<r; i++) {
                int count = 0;
                for (int j=l; j<r; j++) {
                    if (graph[i][j]) {
                        int c = decoy[j];
                        if (c > maxCap) {
                            c = maxCap;
                        }
                        count += c;
                        if (count >= targetCount) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
    
    protected double getP(final DetectedInDecoy detectedInDecoy, int[][] decoyCountsList) {
        final AtomicInteger d = new AtomicInteger(0);
        
        int nDecoy = decoyCountsList.length;
        int nThread = 50;
        int bin = nDecoy / nThread;
        Thread[] threads = new Thread[nThread];
        for (int i = 0; i < nThread; i++) {
            final int[][] decoys = java.util.Arrays.copyOfRange(decoyCountsList, i*bin, (i+1)*bin);
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int[] decoy : decoys) {
                        if (detectedInDecoy.isDetectedInDecoy(decoy)) {
                            d.incrementAndGet();
                        }
                    }
                }
            });
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            }catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        
        return 1.0 * d.get() / decoyCountsList.length;
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
