/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
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
//        args = new String[] {
//            "/Users/jgao/projects/tcga-pancan/filtering/example.maf",
////            "/Users/jgao/projects/tcga-pancan/filtering/mc3.v0.2.8.PUBLIC.code.filtered.essential-cols.sorted.maf",
//            "/Users/jgao/projects/tcga-pancan/filtering/example.results.txt"
//        };
        
        InputStream isFa = HotspotMain.class.getResourceAsStream("/data/Homo_sapiens.GRCh38.pep.all.fa");
        Map<String, Protein> proteins = EnsemblUtils.readFasta(isFa);
        
        Map<String, Set<String>> mafFilter = Collections.singletonMap("Variant_Classification", Collections.singleton("Missense_Mutation"));
        SortedMafReader mafReader = new SortedMafReader(new FileInputStream(args[0]), mafFilter, proteins);
//        testReadMaf(mafReader);
        process(mafReader, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters(), args[1]);
    }
    
    private static void testReadMaf(SortedMafReader mafReader) throws IOException {
        int count = 0;
        System.out.println("Processing proteins...");
        while (mafReader.hasNext()) {
            MutatedProtein mutatedProtein = mafReader.next();

            System.out.println(""+(++count)+"."+mutatedProtein.getGeneSymbol()+": "+mutatedProtein.getMutations().size()+" mutations");
        }
    }
    
    static void process(SortedMafReader mafReader, HotspotDetectiveParameters params, String dirFile) throws IOException {
        process(mafReader, params, new File(dirFile));
    }
    
    static void process(SortedMafReader mafReader, HotspotDetectiveParameters params, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("gene\ttranscript\tprotein_change\tscore\tpvalue\tqvalue\tinfo\n");

            HotspotDetective hd = new ProteinStructureHotspotDetective(params);

            int idHotspot = 0;
            int count = 0;
            System.out.println("Processing proteins...");
            while (mafReader.hasNext()) {
                MutatedProtein mutatedProtein = mafReader.next();
                
                System.out.println(""+(++count)+"."+mutatedProtein.getGeneSymbol()+": "+mutatedProtein.getMutations().size()+" mutations");
                
                if (mutatedProtein.getMutations().size()<5) {
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

                Collection<MutatedResidue> mutatedResidues = mutatedResiduesOnAProtein(mutatedProtein, hotspots,
                        params.getPValueThreshold());
                for (MutatedResidue mr : mutatedResidues) {
                    writer.append(mr.toString());
                    writer.newLine();
                }

                writer.flush();
            }
        }
    }
    
    private static Collection<MutatedResidue> mutatedResiduesOnAProtein(MutatedProtein mutatedProtein,
            Set<Hotspot> hotspots, double pvalueThreahold) {
        SortedMap<Integer, MutatedResidue> map = new TreeMap<>();
        for(Hotspot hs : hotspots) {
            Set<Integer> residues = hs.getResidues();
            for (Integer r : residues) {
                MutatedResidue mutatedResidue = map.get(r);
                if (mutatedResidue==null) {
                    mutatedResidue = new MutatedResidueImpl(mutatedProtein, r, pvalueThreahold);
                    map.put(r, mutatedResidue);
                }
                mutatedResidue.addHotspot(hs);
            }
        }
        return map.values();
    }
}
