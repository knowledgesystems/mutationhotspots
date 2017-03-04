/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;

/**
 *
 * @author jgao
 */
public class Hotspot3DImpl extends HotspotImpl implements Hotspot3D {
    private Set<Hotspot> hotspots3D;
    
    public Hotspot3DImpl(MutatedProtein protein) {
        super(protein);
        this.hotspots3D = new HashSet<Hotspot>();
    }
    
    public Hotspot3DImpl(MutatedProtein protein, SortedSet<Integer> residues) {
        super(protein, residues);
        this.hotspots3D = new HashSet<Hotspot>();
    }
    
    public Hotspot3DImpl(MutatedProtein protein, SortedSet<Integer> residues, Set<Hotspot> hotspots3D) {
        // assume that all of them on the same protein and residues
        super(protein, residues);
        this.hotspots3D = hotspots3D;
    }
    
    @Override
    public Set<Hotspot> getHotspots3D() {
        return Collections.unmodifiableSet(hotspots3D);
    }
    
    public void addHotspots3D(Hotspot hotspot3D) {
        hotspots3D.add(hotspot3D);
    }
    
    @Override
    public String getLabel() {
        StringBuilder sb = new StringBuilder(super.getLabel());
        sb.append("#");
        for (Hotspot hs : hotspots3D) {
            MutatedProtein3D protein = MutatedProtein3D.class.cast(hs.getProtein());
            sb.append(protein.getPdbId()).append('_').append(protein.getPdbChain())
                    .append("(").append(hs.getPValue()).append(");");
        }
        
        
        sb.append(" (p=").append(getPValue()).append(")");
        

        return sb.toString();
    }
    
    @Override
    public double getPValue() {
        if (hotspots3D.isEmpty()) {
            return Double.NaN;
        }
        
        if (Double.isNaN(pvalue)) {
            double pMin = Double.POSITIVE_INFINITY;
            for (Hotspot hotspot : hotspots3D) {
                double p = hotspot.getPValue();
                if (!Double.isNaN(p) && p < pMin) {
                    pMin = p;
                }
            }
            
            if (pMin == Double.POSITIVE_INFINITY) {
                return Double.NaN;
            }
            
            return pMin;
        }
        
        return pvalue;
    }
}