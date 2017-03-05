/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedResidueImpl;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.ProteinStructureHotspotDetective;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.EnsemblUtils;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.SortedMafReader;

/**
 *
 * @author jgao
 */
public class HotspotMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        args = new String[] {
            "/Users/jgao/projects/tcga-pancan/filtering/example.maf",
            "/Users/jgao/projects/tcga-pancan/filtering/cancer_type.txt",
            "/Users/jgao/projects/tcga-pancan/filtering/results"
        };
        
        InputStream isFa = HotspotMain.class.getResourceAsStream("/data/Homo_sapiens.GRCh38.pep.all.fa");
        Map<String, Protein> proteins = EnsemblUtils.readFasta(isFa);
        
//        InputStream isMaf = HotspotMain.class.getResourceAsStream("/data/example.maf");
        
        
        processTCGAPancan( args[0], args[1], args[2], proteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters() );
        
//        process(mutatedProteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters(), "/Users/jgao/Downloads/hotspots.out.txt");
    }
    
    private static void processTCGAPancan(String mafFile, String cancerTypeFile, String outDir, Map<String, Protein> proteins, HotspotDetectiveParameters params) throws IOException {
        Map<String, Map<String, Set<String>>> mafFilters = getMafFilters(cancerTypeFile);
        try (FileInputStream fis = new FileInputStream(mafFile)) {
            for (Map.Entry<String, Map<String, Set<String>>> entry : mafFilters.entrySet()) {
                String outFile = outDir + "/" + entry.getKey();
                System.out.println(entry.getKey());
                SortedMafReader mafReader = new SortedMafReader(fis, entry.getValue(), proteins);
                process(mafReader, params, outFile);
            }
        }
    }
    
    private static Map<String, Map<String, Set<String>>> getMafFilters(String cancerTypeFile) throws IOException {
        Map<String, Map<String, Set<String>>> map = new HashMap<>();
        map.put("PANCAN.txt", Collections.singletonMap("Variant_Classification", Collections.singleton("Missense_Mutation")));
//        try (BufferedReader br = new BufferedReader(new FileReader(cancerTypeFile))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                Map<String, Set<String>> filter = new HashMap<>();
//                filter.put("Variant_Classification", Collections.singleton("Missense_Mutation"));
//                filter.put("CODE", Collections.singleton(line));
//                map.put(line+".txt", filter);
//            }
//        }
        return map;
    }
    
    private static void process(SortedMafReader mafReader, HotspotDetectiveParameters params, String dirFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirFile))) {
            writer.append("gene\ttranscript\tprotein_change\tscore\tpvalue\tqvalue\tinfo\n");

            HotspotDetective hd = new ProteinStructureHotspotDetective(params);

            int idHotspot = 0;
            int count = 0;
            System.out.println("Processing proteins...");
            while (mafReader.hasNext()) {
                MutatedProtein mutatedProtein = mafReader.next();
                
                System.out.println(""+(++count)+"."+mutatedProtein.getGeneSymbol());
                
                if (mutatedProtein.getMutations().isEmpty()) {
                    System.out.println("\tNo mutations");
                    continue;
                }
                
                Set<Hotspot> hotspots;
                try {
                    hotspots = hd.detectHotspots(mutatedProtein);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
                for (Hotspot hotspot : hotspots) {
                    if (hotspot.getPValue()<=params.getPValueThreshold()) {
                        hotspot.setId(++idHotspot);
                    }
//                    System.out.println(hotspot.getLabel());
                }

                Collection<MutatedResidue> mutatedResidues = mutatedResiduesOnAProtein(mutatedProtein, hotspots);
                for (MutatedResidue mr : mutatedResidues) {
                    writer.append(mr.toString());
                    writer.newLine();
                }

                writer.flush();
            }
        }
    }
    
    private static Collection<MutatedResidue> mutatedResiduesOnAProtein(MutatedProtein mutatedProtein, Set<Hotspot> hotspots) {
        SortedMap<Integer, MutatedResidue> map = new TreeMap<>();
        for(Hotspot hs : hotspots) {
            Set<Integer> residues = hs.getResidues();
            for (Integer r : residues) {
                MutatedResidue mutatedResidue = map.get(r);
                if (mutatedResidue==null) {
                    mutatedResidue = new MutatedResidueImpl(mutatedProtein, r);
                    map.put(r, mutatedResidue);
                }
                mutatedResidue.addHotspot(hs);
            }
        }
        return map.values();
    }
}
