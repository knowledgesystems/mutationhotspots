/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat;

/**
 *
 * @author jgao
 */
public interface DetectedInDecoy {
    boolean isDetectedInDecoy(final int[] decoy);
}
