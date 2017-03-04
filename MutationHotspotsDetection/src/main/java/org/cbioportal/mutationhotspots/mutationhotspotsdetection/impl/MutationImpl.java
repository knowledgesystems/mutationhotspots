
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.Objects;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;

/**
 *
 * @author jgao
 */
public class MutationImpl implements Mutation {
    private String gene;
    private String uniprotAcc;
    private String mutationType;
    private int proteinStart;
    private int proteinEnd;
    private String proteinChange;
    private String patient;

    public MutationImpl(String gene, String uniprotAcc, String mutationType,
            int proteinStart, int proteinEnd, String proteinChange, String patient) {
        this.gene = gene;
        this.uniprotAcc = uniprotAcc;
        this.mutationType = mutationType;
        this.proteinStart = proteinStart;
        this.proteinEnd = proteinEnd;
        this.proteinChange = proteinChange;
        this.patient = patient;
    }

    @Override
    public String getGene() {
        return gene;
    }

    @Override
    public void setGene(String gene) {
        this.gene = gene;
    }

    @Override
    public String getUniprotAcc() {
        return uniprotAcc;
    }

    @Override
    public void setUniprotAcc(String uniprotAcc) {
        this.uniprotAcc = uniprotAcc;
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
