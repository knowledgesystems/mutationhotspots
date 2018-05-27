/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure;

import java.util.ArrayList;
import java.util.List;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.ContactMap;
import java.util.Map;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProteinImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jgao
 */
public class ProteinStructureContactMapCalculatorTest {
    
    private List<MutatedProtein> proteins;
    
    public ProteinStructureContactMapCalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        proteins = new ArrayList<>();
        
        MutatedProtein mutatedProtein;
        
        mutatedProtein = new MutatedProteinImpl();
        mutatedProtein.setGeneSymbol("AKT1");
        mutatedProtein.setProteinId("ENSP00000270202.4");
        mutatedProtein.setUniprotAcc("P31749");
        mutatedProtein.setLength(480);
        proteins.add(mutatedProtein);
        
        mutatedProtein = new MutatedProteinImpl();
        mutatedProtein.setGeneSymbol("MAP2K1");
        mutatedProtein.setProteinId("ENSP00000302486.4");
        mutatedProtein.setUniprotAcc("Q02750");
        mutatedProtein.setLength(393);
        proteins.add(mutatedProtein);
    }
    
    @After
    public void tearDown() {
    }

    //@Test
    public void testGetContactMapsFromPDB() {
        System.out.println("getContactMapsPDB");
        
        ProteinStructureContactMapCalculator instance = ProteinStructureContactMapCalculator.getPDBCalculator();
        double identpThreshold = 90;
        double distanceThresholdClosestAtoms = 5.0;
        
        proteins.forEach(mutatedProtein -> {
            Map<MutatedProtein3D, ContactMap> result = instance.getContactMaps(mutatedProtein, identpThreshold, distanceThresholdClosestAtoms);
        });
        
        System.out.println("done");
    }

    @Test
    public void testGetContactMapsFromSwissModel() {
        System.out.println("getContactMapsFromSwissModel");
        
        ProteinStructureContactMapCalculator instance = ProteinStructureContactMapCalculator.getSwissModelCalculator();
        double identpThreshold = 90;
        double distanceThresholdClosestAtoms = 5.0;
        
        proteins.forEach(mutatedProtein -> {
            Map<MutatedProtein3D, ContactMap> result = instance.getContactMaps(mutatedProtein, identpThreshold, distanceThresholdClosestAtoms);
        });
        
        System.out.println("done");
    }
    
}
