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

    String getGeneId();

    String getGeneSymbol();

    String getProteinId();

    int getLength();

//    String getSequence();

    String getTranscriptId();

    String getUniprotAcc();

    void setGeneId(String geneId);

    void setGeneSymbol(String geneSymbol);

    void setProteinId(String proteinId);

    void setLength(int length);
    
//    void setSequence(String proteinSequence);

    void setTranscriptId(String transcriptId);

    void setUniprotAcc(String uniprotAcc);
    
}
