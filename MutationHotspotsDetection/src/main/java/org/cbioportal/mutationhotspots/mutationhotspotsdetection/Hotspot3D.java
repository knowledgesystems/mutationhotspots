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
public interface Hotspot3D extends Hotspot {
    /**
     * 
     * @return 
     */
    public Set<Hotspot> getHotspots3D();
    
    public Set<MutatedProtein3D> getAllMutatedProteins();
}
