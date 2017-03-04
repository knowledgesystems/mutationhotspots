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
public interface MutatedProtein {
    /**
     * 
     * @return 
     */
    public String getGene();
    
    /**
     * 
     * @return Uniprot Acc, e.g. P04637
     */
    public String getUniprotAcc(); 
    
    /**
     * 
     * @return 
     */
    public int getProteinLength();
    
    /**
     * 
     * @return 
     */
    public String getProteinSequence();
    
    /**
     * 
     * @return 
     */
    public List<Mutation> getMutations();
    
    /**
     * 
     */
    public void addMutation(Mutation mutation);
}
