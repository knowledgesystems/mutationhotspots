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
import java.util.HashSet;
import java.util.Set;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.ProteinStructureHotspotDetective;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.MafReader;

/**
 *
 * @author jgao
 */
public class HotspotMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HotspotException {
        Set<String> mutationTypeFilter = Collections.singleton("Missense_Mutation");
        
        InputStream is = HotspotMain.class.getResourceAsStream("/data/MAP2K1.maf");
        
        Collection<MutatedProtein> mutatedProteins = MafReader.readMaf(is, mutationTypeFilter);
        
        HotspotDetectiveParameters params = HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters();
        params.setIdentpThresholdFor3DHotspots(100.0);
        
        HotspotDetective hd = new ProteinStructureHotspotDetective(params);
        
        Set<Hotspot> hotspots = new HashSet<>();
        
        for (MutatedProtein mutatedProtein : mutatedProteins) {
            hotspots.addAll(hd.detectHotspots(mutatedProtein));
        }
        
        for (Hotspot hs : hotspots) {
            System.out.println(hs.getLabel());
        }
    }
    
}
