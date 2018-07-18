/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;

/**
 *
 * @author jgao
 */
public class ProteinImpl implements Protein {
    private String geneSymbol;
    private String geneId;
    private String transcriptId;
    private String proteinId;
    private String uniprotAcc;
//    private String sequence;
    private int length;

    public ProteinImpl() {
    }
    
    public ProteinImpl(Protein protein) {
        this.geneSymbol = protein.getGeneSymbol();
        this.geneId = protein.getGeneId();
        this.transcriptId = protein.getTranscriptId();
        this.proteinId = protein.getProteinId();
        this.uniprotAcc = protein.getUniprotAcc();
//        this.sequence = protein.getSequence();
        this.length = protein.getLength();
    }

    @Override
    public String getGeneSymbol() {
        return geneSymbol;
    }
    
    @Override
    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    @Override
    public String getGeneId() {
        return geneId;
    }

    @Override
    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    @Override
    public String getTranscriptId() {
        return transcriptId;
    }

    @Override
    public void setTranscriptId(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    @Override
    public String getProteinId() {
        return proteinId;
    }

    @Override
    public void setProteinId(String proteinId) {
        this.proteinId = proteinId;
    }

    @Override
    public final String getUniprotAcc() {
        return uniprotAcc;
    }

    @Override
    public final void setUniprotAcc(String uniprotAcc) {
        this.uniprotAcc = uniprotAcc;
        if (uniprotAcc != null) {
            try {
                setUniProt();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }

//    @Override
//    public String getSequence() {
//        return sequence;
//    }

//    @Override
//    public void setSequence(String proteinSequence) {
//        this.sequence = proteinSequence;
//    }

    protected void setUniProt() throws Exception {
        String strURL = "https://www.uniprot.org/uniprot/" + uniprotAcc + ".fasta";
        try (final InputStream in = new URL(strURL).openStream()) {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            String line = buf.readLine();
            if (line == null || !line.startsWith(">")) {
                throw new Exception("unable to read");
            }
            {
                String[] parts = line.split("\\|");
                if (parts[0].equals(">sp")) {
                    uniprotAcc = parts[1]; // in case uniprot id was used.
                } else {
                    System.err.println("Not a SP entry: " + strURL);
                }
            }
            
            if (this.length == 0) {
                while ((line = buf.readLine()) != null) {
                    this.length += line.trim().length();
                }
            }
        }
    }

    @Override
    public String toString() {
        return geneSymbol + "_" + proteinId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProteinImpl other = (ProteinImpl) obj;
        if (!Objects.equals(this.proteinId, other.proteinId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.proteinId);
        return hash;
    }
    
}
