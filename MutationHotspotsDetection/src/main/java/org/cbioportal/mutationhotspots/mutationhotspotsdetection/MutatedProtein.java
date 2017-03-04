/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.util.List;

/**
 *
 * @author jgao
 */
public interface MutatedProtein extends Protein {
    
    /**
     * 
     * @return 
     */
    public List<Mutation> getMutations();
    
    /**
     * 
     * @param mutation
     */
    public void addMutation(Mutation mutation);
}
