/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;
//import umontreal.iro.lecuyer.probdist.NegativeBinomialDist;

/**
 *
 * @author jgao
 */
public class HotspotImpl implements Hotspot {
    private int id;
    private MutatedProtein protein;
    private SortedSet<Integer> residues;
    private Set<Mutation> mutations;
    private Set<String> patients;
    private String label;
    protected double pvalue;
    
    public HotspotImpl(MutatedProtein protein) {
        this(protein, new TreeSet<Integer>());
    }

    /**
     * 
     * @param gene
     * @param residues
     * @param label 
     */
    public HotspotImpl(MutatedProtein protein, SortedSet<Integer> residues) {
        this.protein = protein;
        this.residues = residues;
        this.mutations = new HashSet<Mutation>();
        this.patients = new HashSet<String>();
        this.pvalue = Double.NaN;
    }
    
    @Override
    public void mergeHotspot(Hotspot hotspot) {
        if (hotspot!=null) {
            for (Mutation mutation : hotspot.getMutations()) {
                addMutation(mutation);
                residues.addAll(hotspot.getResidues());
            }
            this.pvalue = Double.NaN;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return gene
     */
    @Override
    public MutatedProtein getProtein() {
        return protein;
    }
    
    /**
     * 
     * @return residues
     */
    @Override
    public SortedSet<Integer> getResidues() {
        return Collections.unmodifiableSortedSet(residues);
    }
    
    @Override
    public Set<Mutation> getMutations() {
        return Collections.unmodifiableSet(mutations);
    }

    @Override
    public void addMutation(Mutation mutation) {
        if (mutations.contains(mutation)) {
            return;
        }
        mutations.add(mutation);
        // do not added residues because we may only want to label part of the residues
        patients.add(mutation.getPatient());
    }

    @Override
    public Set<String> getPatients() {
        return Collections.unmodifiableSet(patients);
    }

    /**
     * 
     * @param label 
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getLabel() {
        if (label != null) {
            return label;//+" (p="+String.format("%6.3e", getPValue()) + ")";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(protein.getGeneSymbol()).append(" ");
        
        String sequence = protein.getSequence();
        Map<Integer, Set<Mutation>> mapResidueMuations = getMapResidueMutations();
        for (Integer res : residues) {
            Set<Mutation> muts = mapResidueMuations.get(res);
            String aa = null;
            try {
                aa = guessAminoAcid(res, muts);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (aa==null&&sequence!=null&&sequence.length()>=res) {
                aa = sequence.substring(res-1,res);
            }
            if (aa!=null) {
                sb.append(aa);
            }
            sb.append(res.toString());
            sb.append("(").append(muts==null?0:muts.size()).append(");");
        }
        sb.deleteCharAt(sb.length()-1);
        
        if (!Double.isNaN(pvalue)) {
            sb.append(" (p=").append(String.format("%6.3e", getPValue())).append(")");
        }

        return sb.toString();
    }
    
    private String guessAminoAcid(int start, Set<Mutation> mutations) {
        for (Mutation mutation : mutations) {
            String proteinChange = mutation.getProteinChange();
            if (proteinChange.matches("([A-Z\\*])"+start+".*")) {
                return proteinChange.substring(0,1);
            }
        }
        return null;
    }
    
    private Map<Integer, Set<Mutation>> getMapResidueMutations() {
        Map<Integer, Set<Mutation>> map = new HashMap<Integer, Set<Mutation>>();
        
        for (Mutation mut : mutations) {
            int start = mut.getProteinStart();
            int end = mut.getProteinEnd();
            for (int res=start; res<=end; res++) {
                Set<Mutation> muts = map.get(res);
                if (muts==null) {
                    muts = new HashSet<Mutation>();
                    map.put(res, muts);
                }
                muts.add(mut);
            }
            
        }
        
        return map;
    }
    
    @Override
    public void setPValue(double pvalue) {
        this.pvalue = pvalue;
    }
    
    @Override
    public double getPValue() {
        if (Double.isNaN(pvalue)) {
            int hotspotLength = residues.size();
            int proteinLength = protein.getLength();
            
            if (proteinLength<=0) {
                System.err.println("Protein length is not available for "+protein.getGeneSymbol()
                        +"("+protein.getProteinId()+")");
                return Double.NaN;
            }

            pvalue = binomialTest(getMutations().size(), protein.getMutations().size(), hotspotLength, proteinLength);
        }
        return pvalue;
    }
    
    private double binomialTest(int numberOfMutationInHotspot, int numberOfAllMutations, int hotspotLength, int proteinLength) {
        double p = 1.0 * hotspotLength / proteinLength;
        BinomialDistribution distribution = new BinomialDistribution(numberOfAllMutations, p);
        return 1- distribution.cumulativeProbability(numberOfMutationInHotspot-1);
    }
    
//    private double negativeBinomialTest(int numberOfMutationInHotspot, int numberOfAllMutations, int hotspotLength, int proteinLength) {
//        double p = 1.0 * numberOfAllMutations / proteinLength / numberOfSeqeuncedSamples;
//        int r = numberOfMutationInHotspot;
//        int k = hotspotLength*numberOfSeqeuncedSamples - r;
//        return NegativeBinomialDist.cdf(k, p, r);
////        return 1 - Beta.regularizedBeta(p, k+1, r);
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.protein != null ? this.protein.hashCode() : 0);
        hash = 43 * hash + (this.mutations != null ? this.mutations.hashCode() : 0);
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
        final HotspotImpl other = (HotspotImpl) obj;
        if (this.protein != other.protein && (this.protein == null || !this.protein.equals(other.protein))) {
            return false;
        }
        if (this.mutations != other.mutations && (this.mutations == null || !this.mutations.equals(other.mutations))) {
            return false;
        }
        return true;
    }
}