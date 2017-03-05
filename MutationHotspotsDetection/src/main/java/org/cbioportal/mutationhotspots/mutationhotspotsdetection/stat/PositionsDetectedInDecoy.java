/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat;

import java.util.Set;

/**
 *
 * @author jgao
 */
public class PositionsDetectedInDecoy implements DetectedInDecoy {
    private final int targetCount;
    private final Set<Integer> residues;
    public PositionsDetectedInDecoy(final int targetCount, final Set<Integer> residues) {
        this.targetCount = targetCount;
        this.residues = residues;
    }
    public boolean isDetectedInDecoy(final int[] decoy) {
        int count = 0;
        for (int r : residues) {
            count += decoy[r];
            if (count>targetCount) {
                return true;
            }
        }
        return false;
    }
}
