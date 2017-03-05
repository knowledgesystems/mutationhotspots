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
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;

/**
 *
 * @author jgao
 */
public class MutatedProteinImpl extends ProteinImpl implements MutatedProtein {
    private List<Mutation> mutations;
    
    public MutatedProteinImpl() {
        super();
        this.mutations = new ArrayList<>();
    }
    
    public MutatedProteinImpl(Protein protein) {
        super(protein);
        this.mutations = new ArrayList<>();
    }
    
    public MutatedProteinImpl(MutatedProtein protein) {
        super(protein);
        this.mutations = new ArrayList<>(protein.getMutations());
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