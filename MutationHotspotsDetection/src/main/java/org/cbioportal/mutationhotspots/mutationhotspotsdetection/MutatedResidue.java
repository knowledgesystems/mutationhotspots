/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.util.Set;

/**
 *
 * @author jgao
 */
public interface MutatedResidue {

    void addHotspot(Hotspot hotspot);

    Set<Hotspot> getHotspots();

    Protein getProtein();

    double getPvalue();

    int getResidue();
    
}
