/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

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
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.MafReader;

/**
 *
 * @author jgao
 */
public class HotspotMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        InputStream isFa = HotspotMain.class.getResourceAsStream("/data/Homo_sapiens.GRCh38.pep.all.fa");
        Map<String, Protein> proteins = EnsemblUtils.readFasta(isFa);
        
        InputStream isMaf = HotspotMain.class.getResourceAsStream("/data/example.maf");
        
        Map<String, Set<String>> mafFilter = new HashMap<>();
        mafFilter.put("Variant_Classification", Collections.singleton("Missense_Mutation"));
        Collection<MutatedProtein> mutatedProteins = MafReader.readMaf(isMaf, mafFilter, proteins);
        
        process(mutatedProteins);
    }
    
    private static void process(Collection<MutatedProtein> mutatedProteins) {
        HotspotDetectiveParameters params = HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters();
//        params.setIdentpThresholdFor3DHotspots(90.0);
//        params.setPValueThreshold(0.1);
        
        HotspotDetective hd = new ProteinStructureHotspotDetective(params);
                
        int idHotspot = 0;
        int count = 0;
        System.out.println("Processing "+mutatedProteins.size()+" proteins...");
        for (MutatedProtein mutatedProtein : mutatedProteins) {
            System.out.println(""+(++count)+"."+mutatedProtein.getGeneSymbol());
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
                System.out.println(hotspot.getLabel());
            }
            
            Collection<MutatedResidue> mutatedResidues = mutatedResiduesOnAProtein(mutatedProtein, hotspots);
            mutatedResidues.forEach((mutatedResidue) -> {System.out.println(mutatedResidue);});
            
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
