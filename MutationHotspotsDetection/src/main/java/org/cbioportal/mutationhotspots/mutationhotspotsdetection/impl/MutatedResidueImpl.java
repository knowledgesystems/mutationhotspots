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
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
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
    private double pvalueThreahold;

    public MutatedResidueImpl(Protein protein, int residue, double pvalueThreahold) {
        this.protein = protein;
        this.residue = residue;
        hotspots = new HashSet<>();
        pvalue = 1.0;
        this.pvalueThreahold = pvalueThreahold;
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
    
    public boolean isSignificant() {
        return pvalue <= pvalueThreahold;
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
                .append("\t")
                .append(pvalue)
                .append("\t")
                .append(".") //qvalue
                ;
            
        TreeSet<Integer> ids = new TreeSet<>();
        for (Hotspot hs : hotspots) {
            if (hs.getPValue()<=pvalueThreahold) {
                ids.add(hs.getId());
            }
        }
        
        if (!ids.isEmpty()) {
            sb.append("\tCLUSTER_ID=").append(StringUtils.join(ids, ","));
        }
        
        TreeSet<String> pdbChains = new TreeSet<>();
        for (Hotspot hs : hotspots) {
            if (hs.getId()>0) {
                Hotspot3D hs3D = Hotspot3D.class.cast(hs);
                Set<Hotspot> phss = hs3D.getHotspots3D();
                for (Hotspot phs : phss) {
                    if (phs.getPValue()<=pvalueThreahold) {
                        MutatedProtein3D p3d = MutatedProtein3D.class.cast(phs.getProtein());
                        pdbChains.add(p3d.getPdbId()+"."+p3d.getPdbChain());
                    }
                }
            }
        }
        if (!pdbChains.isEmpty()) {
            sb.append(";PDB_CHAIN=").append(StringUtils.join(pdbChains, ","));
        }
        
        return sb.toString();
    }
    
    
}
