
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;

/**
 *
 * @author jgao
 */
public class MutationImpl implements Mutation {
    private Protein protein;
    private String mutationType;
    private int proteinStart;
    private int proteinEnd;
    private String proteinChange;
    private String patient;

    public MutationImpl(Protein protein, String mutationType,
            int proteinStart, int proteinEnd, String proteinChange, String patient) {
        this.protein = protein;
        this.mutationType = mutationType;
        this.proteinStart = proteinStart;
        this.proteinEnd = proteinEnd;
        this.proteinChange = proteinChange;
        this.patient = patient;
    }

    @Override
    public Protein getProtein() {
        return protein;
    }

    @Override
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    @Override
    public String getMutationType() {
        return mutationType;
    }

    @Override
    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    @Override
    public int getProteinStart() {
        return proteinStart;
    }

    @Override
    public void setProteinStart(int proteinStart) {
        this.proteinStart = proteinStart;
    }

    @Override
    public int getProteinEnd() {
        return proteinEnd;
    }

    @Override
    public void setProteinEnd(int proteinEnd) {
        this.proteinEnd = proteinEnd;
    }

    @Override
    public String getProteinChange() {
        return proteinChange;
    }

    @Override
    public void setProteinChange(String proteinChange) {
        this.proteinChange = proteinChange;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
    
}
