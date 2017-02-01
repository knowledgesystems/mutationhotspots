
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

/**
 *
 * @author jgao
 */
public interface Mutation {

    String getUniprotAcc();

    String getGene();

    String getMutationType();

    String getProteinChange();

    int getProteinEnd();

    int getProteinStart();

    void setUniprotAcc(String uniprotAcc);

    void setGene(String gene);

    void setMutationType(String mutationType);

    void setProteinChange(String proteinChange);

    void setProteinEnd(int proteinEnd);

    void setProteinStart(int proteinStart);

    public String getPatient();

    public void setPatient(String patient);
    
}
