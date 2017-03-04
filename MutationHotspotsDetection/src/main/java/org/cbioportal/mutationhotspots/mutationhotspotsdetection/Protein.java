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
public interface Protein {
    /**
     * 
     * @return 
     */
    public String getGene();
    
    public String getGeneId();
    
    public String getTranscriptId();
    
    public String getProteinId();
    
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
}
