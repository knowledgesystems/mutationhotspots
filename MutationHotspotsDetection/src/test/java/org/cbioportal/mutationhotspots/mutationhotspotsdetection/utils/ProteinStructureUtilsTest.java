/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

import java.util.Map;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProteinImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jgao
 */
public class ProteinStructureUtilsTest {
    
    public ProteinStructureUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getContactMaps method, of class ProteinStructureUtils.
     */
    @Test
    public void testGetContactMaps() {
        System.out.println("getContactMaps");
        
        String dirPdbCache = "/Users/jgao/projects/cbio-portal-data/reference-data/pdb-cache";
        ProteinStructureUtils instance = new ProteinStructureUtils(dirPdbCache);
        
        MutatedProtein mutatedProtein = new MutatedProteinImpl("MAP2K1", "Q02750");
        double identpThreshold = 90;
        double distanceThresholdClosestAtoms = 5.0;
        
//        Map<MutatedProtein3D, ContactMap> expResult = null;
        Map<MutatedProtein3D, ContactMap> result = instance.getContactMaps(mutatedProtein, identpThreshold, distanceThresholdClosestAtoms);
//        assertEquals(expResult, result);
        System.out.println("done");
    }
    
}
