/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

/**
 *
 * @author jgao
 */
public interface MutatedProtein3D extends MutatedProtein {
    
    /**
     * 
     * @return 
     */
    public String getPdbId();
    
    /**
     * 
     * @return 
     */
    public String getPdbChain();
}
