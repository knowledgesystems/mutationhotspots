/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.ArrayList;
import java.util.Collections;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import java.util.List;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;

/**
 *
 * @author jgao
 */
public class MutatedProteinImpl extends ProteinImpl implements MutatedProtein {
    private List<Mutation> mutations;
    
    public MutatedProteinImpl(MutatedProtein protein) {
        this.gene = protein.getGene();
        this.uniprotAcc = protein.getUniprotAcc();
        proteinLength = protein.getProteinLength();
        this.mutations = new ArrayList<>(protein.getMutations());
    }

    public MutatedProteinImpl(String gene, String uniprotAcc) {
        this.gene = gene;
        this.uniprotAcc = uniprotAcc;
        try {
            setUniProt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mutations = new ArrayList<>();
    }


    @Override
    public final List<Mutation> getMutations() {
        return Collections.unmodifiableList(mutations);
    }

    @Override
    public final void addMutation(Mutation mutation) {
        this.mutations.add(mutation);
    }

    
    
}