/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.loader.UniprotProxySequenceReader;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;

/**
 *
 * @author jgao
 */
public class MutatedProteinImpl implements MutatedProtein {
    private String gene;
    private String uniprotAcc;
    private int proteinLength;
    private List<Mutation> mutations;
    
    public MutatedProteinImpl(MutatedProtein protein) {
        this(protein.getGene());
        setUniprotAcc(protein.getUniprotAcc());
        setProteinLength(protein.getProteinLength());
        setMutations(protein.getMutations());
    }

    public MutatedProteinImpl(String gene) {
        this.gene = gene;
    }

    @Override
    public final String getGene() {
        return gene;
    }

    @Override
    public final String getUniprotAcc() {
        return uniprotAcc;
    }

    public final void setUniprotAcc(String uniprotAcc) {
        this.uniprotAcc = uniprotAcc;
    }

    @Override
    public final int getProteinLength() {
        return proteinLength;
    }

    @Override
    public final void setProteinLength(int proteinLength) {
        this.proteinLength = proteinLength;
    }

    @Override
    public final List<Mutation> getMutations() {
        return mutations;
    }

    @Override
    public final void setMutations(List<Mutation> mutations) {
        this.mutations = mutations;
    }

    private static Map<String, String> uniprotSequences = new HashMap<String, String>();
    
    @Override
    public String getProteinSequence() {
        if (!uniprotSequences.containsKey(uniprotAcc)) {
            String seq = null;
            try {
                UniprotProxySequenceReader<AminoAcidCompound> uniprotSequence
                        = new UniprotProxySequenceReader<AminoAcidCompound>(uniprotAcc,
                                AminoAcidCompoundSet.getAminoAcidCompoundSet());
                seq = uniprotSequence.getSequenceAsString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            uniprotSequences.put(uniprotAcc, seq);
        }
        
        return uniprotSequences.get(uniprotAcc);
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.gene != null ? this.gene.hashCode() : 0);
        hash = 17 * hash + (this.uniprotAcc != null ? this.uniprotAcc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutatedProteinImpl other = (MutatedProteinImpl) obj;
        if (this.gene != other.gene && (this.gene == null || !this.gene.equals(other.gene))) {
            return false;
        }
        if ((this.uniprotAcc == null) ? (other.uniprotAcc != null) : !this.uniprotAcc.equals(other.uniprotAcc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(gene);
        if (uniprotAcc!=null) {
            sb.append("_").append(uniprotAcc);
        }
        return sb.toString();
    }
    
    
}