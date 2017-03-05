/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author jgao
 */
public class DecoySignificanceTest {
    
    private final int nThread = 100;
    private final int nMaxDecoysPerThread = 100;
    private final List<DecoyGenerator> decoyGenerators;

    public DecoySignificanceTest(int[] counts, int left, int right) {
        decoyGenerators = new ArrayList<>();
        Random rnd = new Random();
        for (int i=0; i<nThread; i++) {
            Random random = new Random(rnd.nextLong());
            DecoyGenerator decoyGenerator = new DecoyGenerator(counts, left, right, random);
            decoyGenerators.add(decoyGenerator);
        }
    }
    
    private void reset() {
        decoyGenerators.forEach((decoyGenerator) -> {
            decoyGenerator.reset();
        });
    }
    
    public double test(final DetectedInDecoy detectedInDecoy) {
        reset();
        
        final AtomicInteger d = new AtomicInteger(0);
        
        Thread[] threads = new Thread[nThread];
        for (int i = 0; i < nThread; i++) {
            final DecoyGenerator decoyGenerator = decoyGenerators.get(i);
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j=0; j<nMaxDecoysPerThread; j++) {
                        int[] decoy = decoyGenerator.next();
                        if (detectedInDecoy.isDetectedInDecoy(decoy)) {
                            d.incrementAndGet();
                        }
                    }
                }
            });
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            }catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        
        return 1.0 * d.get() / nThread / nMaxDecoysPerThread;
    }
    
}
