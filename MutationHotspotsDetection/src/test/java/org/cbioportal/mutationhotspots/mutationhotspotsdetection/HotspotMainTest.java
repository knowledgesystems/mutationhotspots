/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import static org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotMain.process;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.EnsemblUtils;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.SortedMafReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jgao
 */
public class HotspotMainTest {
    
    public HotspotMainTest() {
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
     * Test of main method, of class HotspotMain.
     */
    @Test
    public void testMain() throws IOException, HotspotException {
        System.out.println("main");
        InputStream isFa = HotspotMain.class.getResourceAsStream("/data/Homo_sapiens.GRCh38.pep.all.fa");
        Map<String, Protein> proteins = EnsemblUtils.readFasta(isFa);
        
        InputStream isMaf = HotspotMain.class.getResourceAsStream("/data/example.maf");
        
        Map<String, Set<String>> mafFilter = Collections.singletonMap("Variant_Classification", Collections.singleton("Missense_Mutation"));
        
        SortedMafReader mafReader = new SortedMafReader(isMaf, mafFilter, proteins);
        
        File temp = File.createTempFile("tempfile", ".tmp");
        
        process(mafReader, HotspotDetectiveParameters.getDefaultHotspotDetectiveParameters(), temp);
        
        System.out.println(temp.getAbsolutePath());
    }
    
}
