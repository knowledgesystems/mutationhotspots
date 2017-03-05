/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat;

import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.ContactMap;

/**
 *
 * @author jgao
 */
public class StructureHotspotDetectedInDecoy implements DetectedInDecoy {
    private final ContactMap contactMap;
    private final int maxCap;
    private final int targetCount;
    public StructureHotspotDetectedInDecoy(final ContactMap contactMap, final int maxCap, final int targetCount) {
        this.contactMap = contactMap;
        this.maxCap = maxCap;
        this.targetCount = targetCount;
    }
    public boolean isDetectedInDecoy(final int[] decoy) {
        boolean[][] graph = contactMap.getContact();
        int l = contactMap.getProteinLeft();
        int r = contactMap.getProteinRight();
        for (int i=l; i<r; i++) {
            int count = 0;
            for (int j=l; j<r; j++) {
                if (graph[i][j]) {
                    int c = decoy[j];
                    if (c > maxCap) {
                        c = maxCap;
                    }
                    count += c;
                    if (count >= targetCount) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
