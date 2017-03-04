
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

/**
 *
 * @author jgao
 */
public interface Mutation {

    Protein getProtein();

    String getMutationType();

    String getProteinChange();

    int getProteinEnd();

    int getProteinStart();

    void setProtein(Protein protein);

    void setMutationType(String mutationType);

    void setProteinChange(String proteinChange);

    void setProteinEnd(int proteinEnd);

    void setProteinStart(int proteinStart);

    public String getPatient();

    public void setPatient(String patient);
    
}
