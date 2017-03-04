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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.loader.UniprotProxySequenceReader;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Protein;

/**
 *
 * @author jgao
 */
public class ProteinImpl implements Protein {
    protected String gene;
    protected String geneId;
    protected String transcriptId;
    protected String proteinId;
    protected String uniprotAcc;
    protected int proteinLength;
    protected String proteinSequence;

    public ProteinImpl() {
    }
    
    public ProteinImpl(Protein protein) {
        this.gene = protein.getGene();
        this.geneId = protein.getGeneId();
        this.transcriptId = protein.getTranscriptId();
        this.proteinId = protein.getProteinId();
        this.uniprotAcc = protein.getUniprotAcc();
        proteinLength = protein.getProteinLength();
    }

    public ProteinImpl(String gene, String uniprotAcc) {
        this.gene = gene;
        this.uniprotAcc = uniprotAcc;
        try {
            setUniProt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public final String getGene() {
        return gene;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public void setTranscriptId(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public String getProteinId() {
        return proteinId;
    }

    public void setProteinId(String proteinId) {
        this.proteinId = proteinId;
    }

    @Override
    public final String getUniprotAcc() {
        return uniprotAcc;
    }

    public final void setUniprotAcc(String uniprotAcc) {
        this.uniprotAcc = uniprotAcc;
    }

    @Override
    public final int getProteinLength() {
        return proteinLength;
    }

    public String getProteinSequence() {
        return proteinSequence;
    }

    public void setProteinSequence(String proteinSequence) {
        this.proteinSequence = proteinSequence;
    }

    protected void setUniProt() throws Exception {
        String strURL = "http://www.uniprot.org/uniprot/" + uniprotAcc + ".fasta";
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
            StringBuilder sb = new StringBuilder();
            while ((line = buf.readLine()) != null) {
                sb.append(line.trim());
            }
            this.proteinSequence = sb.toString();
            proteinLength += proteinSequence.length();
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.gene != null ? this.gene.hashCode() : 0);
        hash = 17 * hash + (this.uniprotAcc != null ? this.uniprotAcc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutatedProteinImpl other = (MutatedProteinImpl) obj;
        if (this.gene != other.gene && (this.gene == null || !this.gene.equals(other.gene))) {
            return false;
        }
        if ((this.uniprotAcc == null) ? (other.uniprotAcc != null) : !this.uniprotAcc.equals(other.uniprotAcc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(gene);
        if (uniprotAcc != null) {
            sb.append("_").append(uniprotAcc);
        }
        return sb.toString();
    }
    
}
