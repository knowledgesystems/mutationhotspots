/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.EnsemblUtils;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.SortedMafReader;

/**
 *
 * @author jgao
 */
public class TCGAPanCanMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        args = new String[] {
//            "/Users/jgao/projects/tcga-pancan/filtering/example.maf",
////            "/Users/jgao/projects/tcga-pancan/filtering/mc3.v0.2.8.PUBLIC.code.filtered.essential-cols.sorted.maf",
//            "/Users/jgao/projects/tcga-pancan/filtering/cancer_type.txt",
//            "/Users/jgao/projects/tcga-pancan/filtering/results"
//        };
        
        InputStream isFa = TCGAPanCanMain.class.getResourceAsStream("/data/Homo_sapiens.GRCh38.pep.all.fa");
        Map<String, Protein> proteins = EnsemblUtils.readFasta(isFa);
        
//        InputStream isMaf = HotspotMain.class.getResourceAsStream("/data/example.maf");
        
//        testMaf( args[0], args[1], args[2], proteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters() );
//        processTCGAPancan( args[0], args[1], args[2], proteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters() );
        processTCGAPancanThreads( args[0], args[1], args[2], proteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters() );
        
//        process(mutatedProteins, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters(), "/Users/jgao/Downloads/hotspots.out.txt");
    }
    
    private static void testMaf(String mafFile, String cancerTypeFile, String outDir, Map<String, Protein> proteins, HotspotDetectiveParameters params) throws IOException {
        
        SortedMafReader mafReader = new SortedMafReader(new FileInputStream(mafFile), 
                Collections.singletonMap("Variant_Classification", Collections.singleton("Missense_Mutation")), proteins);
        testReadMaf(mafReader);
    }
    
    private static void processTCGAPancan(String mafFile, String cancerTypeFile, String outDir, Map<String, Protein> proteins, HotspotDetectiveParameters params) throws IOException {
        Map<String, Map<String, Set<String>>> mafFilters = getMafFilters(cancerTypeFile);
        for (Map.Entry<String, Map<String, Set<String>>> entry : mafFilters.entrySet()) {
            try (FileInputStream fis = new FileInputStream(mafFile)) {
                String outFile = outDir + "/" + entry.getKey();
                System.out.println(entry.getKey());
                SortedMafReader mafReader = new SortedMafReader(fis, entry.getValue(), proteins);
                HotspotMain.process(mafReader, params, outFile);
            }
        }
    }
    
    private static void processTCGAPancanThreads(String mafFile, String cancerTypeFile, String outDir, Map<String, Protein> proteins, HotspotDetectiveParameters params) throws IOException {
        Map<String, Map<String, Set<String>>> mafFilters = getMafFilters(cancerTypeFile);
        
            int nThread = mafFilters.size();
            
            List<Thread> threads = new ArrayList<>(nThread);
            for (Map.Entry<String, Map<String, Set<String>>> entry : mafFilters.entrySet()) {
                String outFile = outDir + "/" + entry.getKey();
                Map<String, Set<String>> filter = entry.getValue();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try (FileInputStream fis = new FileInputStream(mafFile)) {
                            SortedMafReader mafReader = new SortedMafReader(fis, filter, proteins);
                            HotspotMain.process(mafReader, params, outFile);
                        } catch (IOException ex) {
                            Logger.getLogger(TCGAPanCanMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                threads.add(thread);
                thread.start();
            }
            
            for (Thread thread : threads) {
                try {
                    thread.join();
                }catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            
            System.out.println("done");
        
            
            
    }
    
    
    private static Map<String, Map<String, Set<String>>> getMafFilters(String cancerTypeFile) throws IOException {
        Map<String, Map<String, Set<String>>> map = new HashMap<>();
        map.put("PANCAN.txt", Collections.singletonMap("Variant_Classification", Collections.singleton("Missense_Mutation")));
        try (BufferedReader br = new BufferedReader(new FileReader(cancerTypeFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, Set<String>> filter = new HashMap<>();
                filter.put("Variant_Classification", Collections.singleton("Missense_Mutation"));
                filter.put("CODE", Collections.singleton(line));
                map.put(line+".txt", filter);
            }
        }
        return map;
    }
    
    private static void testReadMaf(SortedMafReader mafReader) throws IOException {
        int count = 0;
        System.out.println("Processing proteins...");
        while (mafReader.hasNext()) {
            MutatedProtein mutatedProtein = mafReader.next();

            System.out.println(""+(++count)+"."+mutatedProtein.getGeneSymbol()+": "+mutatedProtein.getMutations().size()+" mutations");
        }
    }
}
