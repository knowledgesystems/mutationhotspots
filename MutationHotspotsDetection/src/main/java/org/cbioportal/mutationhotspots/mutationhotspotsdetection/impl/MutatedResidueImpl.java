/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedResidue;

/**
 *
 * @author jgao
 */
public class MutatedResidueImpl implements MutatedResidue {
    private Protein protein;
    private int residue;
    private Set<Hotspot> hotspots;
    private double pvalue;

    public MutatedResidueImpl(Protein protein, int residue) {
        this.protein = protein;
        this.residue = residue;
        hotspots = new HashSet<>();
        pvalue = 1.0;
    }

    @Override
    public Protein getProtein() {
        return protein;
    }

    @Override
    public int getResidue() {
        return residue;
    }

    @Override
    public Set<Hotspot> getHotspots() {
        return Collections.unmodifiableSet(hotspots);
    }

    @Override
    public void addHotspot(Hotspot hotspot) {
        hotspots.add(hotspot);
        double p = hotspot.getPValue();
        if (p < pvalue) {
            pvalue = p;
        }
    }

    @Override
    public double getPvalue() {
        return pvalue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutatedResidueImpl other = (MutatedResidueImpl) obj;
        if (this.residue != other.residue) {
            return false;
        }
        if (!Objects.equals(this.protein, other.protein)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(protein.getGeneSymbol())
                .append("\t")
                .append(protein.getTranscriptId().split("\\.")[0])
                .append("\t")
                .append(residue)
                .append("\t")
                .append(".") //score
                .append(pvalue)
                .append("\t")
                .append(".") //qvalue
                .append("\t")
                .append("CLUSTER_ID=")
                ;
                
        for (Hotspot hs : hotspots) {
            sb.append(hs.getId()).append(",");
        }
        
        sb.deleteCharAt(sb.length()-1);
        
        return sb.toString();
    }
    
    
}
