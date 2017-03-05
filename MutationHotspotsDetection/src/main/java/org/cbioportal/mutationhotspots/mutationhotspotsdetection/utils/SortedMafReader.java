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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
public final class SortedMafReader {
    
    private static final String HEADER_GENE_SYMBOL = "Hugo_Symbol";
    private static final String HEADER_GENE_ID = "Gene";
    private static final String HEADER_TRANSCRIPT_ID = "Transcript_ID";
    private static final String HEADER_PROTEIN_ID = "ENSP";
    private static final String HEADER_UNIPROT = "SWISSPROT";
    private static final String HEADER_MUTATION_TYPE = "Variant_Classification";
    private static final String HEADER_PROTEIN_POSITION = "Protein_position";
    private static final String HEADER_PROTEIN_CHANGE = "HGVSp_Short";
    private static final String HEADER_PATIENT = "Tumor_Sample_Barcode";
    private static final Pattern PATTERN_POSITION = Pattern.compile("[0-9]+");
    
    private final Map<String,Set<String>> filter;
    private final Map<String, Protein> proteins;
    private final BufferedReader bufferedReader;
    private String currLine;
    private final Map<String, Integer> headers;
    
    public SortedMafReader(InputStream mafIS, Map<String, Set<String>> filter, Map<String, Protein> proteins) throws IOException {
        this.filter = filter;
        this.proteins = proteins;
        this.bufferedReader = new BufferedReader(new InputStreamReader(mafIS));
        this.headers = getHeaders(bufferedReader.readLine());
        nextLine();
    }
    
    public boolean hasNext() {
        return currLine != null;
    }
    
    public MutatedProtein next() throws IOException {
        MutatedProtein mutatedProtein = parseProtein(currLine);
        
        while (currLine != null) {
            String[] parts = currLine.split("\t");
            
            if (parts[headers.get(HEADER_PROTEIN_ID)].split("\\.").length==0) {
                System.err.println("Wrong "+ currLine);
                nextLine();
                break;
            }
            
            String proteinId = parts[headers.get(HEADER_PROTEIN_ID)].split("\\.")[0]; // remove vesion
            if (!proteinId.equals(mutatedProtein.getProteinId().split("\\.")[0])) {
                break; // next protein
            }
            
            try {
                parseMutation(parts, mutatedProtein);
            } catch (Exception e) {
                System.err.print("Problem parse line: "+currLine);
                e.printStackTrace();
            } 

            nextLine();
        }
        
        return mutatedProtein;
    }
    
    /**
     * Find the next line passing filter
     * @throws IOException 
     */
    private void nextLine() throws IOException {
        while ((currLine = bufferedReader.readLine())!=null) {
            String parts[] = currLine.split("\t");
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
            if (include) {
                break;
            }
        }
    }
    
    private MutatedProtein parseProtein(String line) {
        String parts[] = line.split("\t");
        
        String proteinId = parts[headers.get(HEADER_PROTEIN_ID)];
        String uniprot = parts[headers.get(HEADER_UNIPROT)];
        String geneSymbol = parts[headers.get(HEADER_GENE_SYMBOL)];
        String geneId = parts[headers.get(HEADER_GENE_ID)];
        String transcriptId = parts[headers.get(HEADER_TRANSCRIPT_ID)];

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

        return new MutatedProteinImpl(protein);

    }
    
    private void parseMutation(String[] parts, MutatedProtein mutatedProtein){
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
            return;
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
            Matcher m = PATTERN_POSITION.matcher(proteinChange);
            if (m.find()) {
                proteinStart = Integer.parseInt(m.group());
                proteinEnd = proteinStart;
            }
        }

        if (proteinStart==-1) {
            return;
        }

        String patient = parts[headers.get(HEADER_PATIENT)];

        Mutation mu = new MutationImpl(mutatedProtein, mutationType, proteinStart, proteinEnd, proteinChange, patient);
        
        mutatedProtein.addMutation(mu);
    }
    
    private Map<String, Integer> getHeaders(String line) {
        Map<String, Integer> map = new HashMap<>();
        String[] parts = line.split("\t");
        for (int i=0; i<parts.length; i++) {
            String part = parts[i];
            map.put(part, i);
        }
        return map;
    }
}
