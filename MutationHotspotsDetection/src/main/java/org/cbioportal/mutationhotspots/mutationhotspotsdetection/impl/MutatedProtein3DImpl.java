/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;

/**
 *
 * @author jgao
 */
public class MutatedProtein3DImpl extends MutatedProteinImpl implements MutatedProtein3D {
    private String pdbId;
    private String pdbChain;
    
    public MutatedProtein3DImpl(MutatedProtein protein) {
        super(protein);
    }

    @Override
    public String getPdbId() {
        return pdbId;
    }

    public void setPdbId(String pdbId) {
        this.pdbId = pdbId;
    }

    @Override
    public String getPdbChain() {
        return pdbChain;
    }

    public void setPdbChain(String pdbChain) {
        this.pdbChain = pdbChain;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + super.hashCode();
        hash = 17 * hash + (this.pdbId != null ? this.pdbId.hashCode() : 0);
        hash = 17 * hash + (this.pdbChain != null ? this.pdbChain.hashCode() : 0);
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
        final MutatedProtein3DImpl other = (MutatedProtein3DImpl) obj;
        if (!super.equals(other)) {
            return false;
        }
        if ((this.pdbId == null) ? (other.pdbId != null) : !this.pdbId.equals(other.pdbId)) {
            return false;
        }
        if ((this.pdbChain == null) ? (other.pdbChain != null) : !this.pdbChain.equals(other.pdbChain)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (pdbId!=null) {
            sb.append("_").append(pdbId);
        }
        if (pdbChain!=null) {
            sb.append("_").append(pdbChain);
        }
        return sb.toString();
    }
    
    
}