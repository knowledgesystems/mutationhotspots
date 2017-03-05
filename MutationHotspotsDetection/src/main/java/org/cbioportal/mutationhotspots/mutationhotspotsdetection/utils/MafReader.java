/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutatedProteinImpl;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.MutationImpl;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl.ProteinImpl;

/**
 *
 * @author jgao
 */
public final class MafReader {
    
    private static final String HEADER_GENE_SYMBOL = "Hugo_Symbol";
    private static final String HEADER_GENE_ID = "Gene";
    private static final String HEADER_TRANSCRIPT_ID = "Transcript_ID";
    private static final String HEADER_PROTEIN_ID = "ENSP";
    private static final String HEADER_UNIPROT = "SWISSPROT";
    private static final String HEADER_MUTATION_TYPE = "Variant_Classification";
    private static final String HEADER_PROTEIN_POSITION = "Protein_position";
    private static final String HEADER_PROTEIN_CHANGE = "HGVSp_Short";
    private static final String HEADER_PATIENT = "Tumor_Sample_Barcode";
    
    public static Collection<MutatedProtein> readMaf(String dirMaf, Map<String,Set<String>> filter, Map<String, Protein> proteins) throws IOException {
        return readMaf(new File(dirMaf), filter, proteins);
    }
    
    public static Collection<MutatedProtein> readMaf(File mafFile, Map<String,Set<String>> filter, Map<String, Protein> proteins) throws IOException {
        return readMaf(new FileInputStream(mafFile), filter, proteins);
    }
    
    public static Collection<MutatedProtein> readMaf(InputStream mafIS, Map<String,Set<String>> filter, Map<String, Protein> proteins) throws IOException {
        Map<String,MutatedProtein> mutatedProteins = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(mafIS))) {
            String line = br.readLine();
            Map<String, Integer> headers = getHeaders(line);
            
            Pattern pattenPosition = Pattern.compile("[0-9]+");
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                
                boolean include = true;
                for (Map.Entry<String,Set<String>> entry : filter.entrySet()) {
                    String column = entry.getKey();
                    Set<String> valuesFilter = entry.getValue();
                    int i = headers.get(column);
                    String v = parts[i];
                    if (!valuesFilter.contains(v)) {
                        include = false;
                        break;
                    }
                }
                if (!include) {
                    continue;
                }
                
                String mutationType = parts[headers.get(HEADER_MUTATION_TYPE)];
                int proteinStart = -1;
                int proteinEnd = -1;
                
                String proteinChange = parts[headers.get(HEADER_PROTEIN_CHANGE)];

                if (headers.get(HEADER_PROTEIN_POSITION)>=0) {
                    String proteinPosition = parts[headers.get(HEADER_PROTEIN_POSITION)].trim();
                    if (!proteinPosition.isEmpty() && !proteinPosition.equals(".")) {
                        String[] poss = proteinPosition.split("/")[0].split("-");
                        proteinStart = Integer.parseInt(poss[0]);
                        if (poss.length==2) {
                            proteinEnd = Integer.parseInt(parts[1]);
                        } else {
                            proteinEnd = proteinStart;
                        }
                    }
                } else {
                    Matcher m = pattenPosition.matcher(proteinChange);
                    if (m.find()) {
                        proteinStart = Integer.parseInt(m.group());
                        proteinEnd = proteinStart;
                    }
                }
                
                if (proteinStart==-1) {
                    continue;
                }
                
                String patient = parts[headers.get(HEADER_PATIENT)];
                String proteinId = parts[headers.get(HEADER_PROTEIN_ID)];
                String uniprot = parts[headers.get(HEADER_UNIPROT)];
                String geneSymbol = parts[headers.get(HEADER_GENE_SYMBOL)];
                String geneId = parts[headers.get(HEADER_GENE_ID)];
                String transcriptId = parts[headers.get(HEADER_TRANSCRIPT_ID)];
                
                MutatedProtein mutatedProtein = mutatedProteins.get(proteinId);
                if (mutatedProtein==null) {
                    Protein protein = proteins.get(proteinId);
                    if (protein == null) {
                        System.err.println("Couldn't fine "+proteinId);
                        
                        protein = new ProteinImpl();
                        protein.setGeneId(geneId);
                        protein.setGeneSymbol(geneSymbol);
                        protein.setProteinId(proteinId);
                        protein.setTranscriptId(transcriptId);
                    }
                    
                    protein.setUniprotAcc(uniprot);
                    
                    mutatedProtein = new MutatedProteinImpl(protein);
                    
                    mutatedProteins.put(proteinId, mutatedProtein);
                }
                
                Mutation mu = new MutationImpl(mutatedProtein, mutationType, proteinStart, proteinEnd, proteinChange, patient);
                
                mutatedProtein.addMutation(mu);
            }
        }
        
        return mutatedProteins.values();
    }
    
    private static Map<String, Integer> getHeaders(String line) {
        Map<String, Integer> map = new HashMap<>();
        String[] parts = line.split("\t");
        for (int i=0; i<parts.length; i++) {
            String part = parts[i];
            map.put(part, i);
        }
        return map;
    }
}
