/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jgao
 */
public class DecoyGenerator {
    
    private final int[] counts;
    private final List<int[]> decoys;
    private final int left, right;
    private final Random random;
    private int current;
    
    public DecoyGenerator(int[] counts, int left, int right, Random random) {
        this.counts = counts;
        this.left = left;
        this.right = right;
        this.random = random;
        decoys = new LinkedList<>();
        current = 0;
    }
    
    public int[] next() {
        if (current <= decoys.size()) {
            int[] decoy = Arrays.copyOf(counts, counts.length);
            shuffleArray(decoy, left, right);
            decoys.add(decoy);
        }
        return decoys.get(current++);
    }
    
    public void reset() {
        current = 0;
    }
    
    private void shuffleArray(int[] a, int left, int right) {
        for (int i = right; i > left; i--) {
            swap(a, i - 1, random.nextInt(i));
        }
    }
    
    private void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
}
