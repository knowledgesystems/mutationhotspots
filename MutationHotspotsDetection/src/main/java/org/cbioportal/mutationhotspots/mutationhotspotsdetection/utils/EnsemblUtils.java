/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.ProteinImpl;

/**
 *
 * @author jgao
 */
public final class EnsemblUtils {
    private EnsemblUtils() {}
    
    public static String readSequence(String ensemblId) throws Exception {
        String strURL = "https://rest.ensembl.org/sequence/id/"+ensemblId+"?content-type=text/plain";
            StringBuilder sb = new StringBuilder();
        try (final InputStream in = new URL(strURL).openStream()) {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            String line;
            while ((line = buf.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        return sb.toString();
    }
    
    /**
     * 
     * @param is
     * @return Map<proteinId, protein>
     * @throws IOException 
     */
    public static Map<String, Protein> readFasta(InputStream is) throws IOException {
        Map<String, Protein> map = new HashMap<>();
            
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            System.out.println("Reading Ensembl FASTA");
            String line;
            Protein protein = null;
            StringBuilder proteinSeq = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (protein!=null) {
                        protein.setLength(proteinSeq.length());
                        String proteinId = protein.getProteinId().split("\\.")[0]; // remove version
                        map.put(proteinId, protein);
                    }
                    
                    // new protein
                    proteinSeq = new StringBuilder();
                    protein = new ProteinImpl();
                    String[] parts = line.split(" ");
                    String ensp = parts[0].substring(1);
                    protein.setProteinId(ensp);

                    for (String part : parts) {
                        String[] pps = part.split(":",2);
                        if (pps.length==2) {
                            switch (pps[0]) {
                                case "gene":
                                    protein.setGeneId(pps[1]);
                                    break;
                                case "transcript":
                                    protein.setTranscriptId(pps[1]);
                                    break;
                                case "gene_symbol":
                                    protein.setGeneSymbol(pps[1]);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } else {
                    proteinSeq.append(line.trim());
                }
            }
            
            // the last protein
            protein.setLength(proteinSeq.length());
            String proteinId = protein.getProteinId().split("\\.")[0]; // remove version
            map.put(proteinId, protein);
        }
        
        return map;
    }
}
