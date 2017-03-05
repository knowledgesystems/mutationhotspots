/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

import org.cbioportal.mutationhotspots.mutationhotspotsdetection.ContactMap;
import java.util.Map;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein3D;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProteinImpl;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.ProteinImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        
//        String dirPdbCache = "/Users/jgao/projects/cbio-portal-data/reference-data/pdb-cache";
        ProteinStructureUtils instance = ProteinStructureUtils.getInstance();
        
        MutatedProtein mutatedProtein = new MutatedProteinImpl();
        mutatedProtein.setGeneSymbol("MAP2K1");
        mutatedProtein.setProteinId("ENSP00000302486.4");
        mutatedProtein.setLength(393);
//        mutatedProtein.setSequence(  "MPKKKPTPIQLNPAPDGSAVNGTSSAETNLEALQKKLEELELDEQQRKRLEAFLTQKQKV\n" +
//                                            "GELKDDDFEKISELGAGNGGVVFKVSHKPSGLVMARKLIHLEIKPAIRNQIIRELQVLHE\n" +
//                                            "CNSPYIVGFYGAFYSDGEISICMEHMDGGSLDQVLKKAGRIPEQILGKVSIAVIKGLTYL\n" +
//                                            "REKHKIMHRDVKPSNILVNSRGEIKLCDFGVSGQLIDSMANSFVGTRSYMSPERLQGTHY\n" +
//                                            "SVQSDIWSMGLSLVEMAVGRYPIPPPDAKELELMFGCQVEGDAAETPPRPRTPGRPLSSY\n" +
//                                            "GMDSRPPMAIFELLDYIVNEPPPKLPSGVFSLEFQDFVNKCLIKNPAERADLKQLMVHAF\n" +
//                                            "IKRSDAEEVDFAGWLCSTIGLNQPSTPTHAAGV");
        double identpThreshold = 90;
        double distanceThresholdClosestAtoms = 5.0;
        
//        Map<MutatedProtein3D, ContactMap> expResult = null;
        Map<MutatedProtein3D, ContactMap> result = instance.getContactMaps(mutatedProtein, identpThreshold, distanceThresholdClosestAtoms);
//        assertEquals(expResult, result);
        System.out.println("done");
    }
    
}
