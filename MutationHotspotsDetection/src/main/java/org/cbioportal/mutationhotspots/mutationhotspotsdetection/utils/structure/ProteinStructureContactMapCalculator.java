/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure;

import java.util.Map;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.ContactMap;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;

/**
 *
 * @author jgao
 */
public interface ProteinStructureContactMapCalculator {  
    static final ProteinStructureContactMapCalculator pdbCalculator = new ContactMapFromPDB();
    public static ProteinStructureContactMapCalculator getPDBCalculator() {
        return pdbCalculator;
    }
    
    static final ProteinStructureContactMapCalculator swissModelCalculator = new ContactMapFromSwissModel();
    public static ProteinStructureContactMapCalculator getSwissModelCalculator() {
        return swissModelCalculator;
    }

    Map<MutatedProtein3D, ContactMap> getContactMaps(MutatedProtein mutatedProtein, double identpThreshold, double distanceThresholdClosestAtoms);
    
}
