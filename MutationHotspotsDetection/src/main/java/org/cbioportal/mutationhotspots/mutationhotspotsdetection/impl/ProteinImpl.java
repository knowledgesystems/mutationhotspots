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
    
    protected static Map<String, String> uniprotSequences = new HashMap<String, String>();
    protected String gene;
    protected String geneId;
    protected String transcriptId;
    protected String proteinId;
    protected String uniprotAcc;
    protected int proteinLength;

    public ProteinImpl() {
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
            proteinLength = 0;
            while ((line = buf.readLine()) != null) {
                proteinLength += line.trim().length();
            }
        }
    }

    @Override
    public String getProteinSequence() {
        if (!uniprotSequences.containsKey(uniprotAcc)) {
            String seq = null;
            try {
                UniprotProxySequenceReader<AminoAcidCompound> uniprotSequence = new UniprotProxySequenceReader<AminoAcidCompound>(uniprotAcc, AminoAcidCompoundSet.getAminoAcidCompoundSet());
                seq = uniprotSequence.getSequenceAsString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            uniprotSequences.put(uniprotAcc, seq);
        }
        return uniprotSequences.get(uniprotAcc);
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
