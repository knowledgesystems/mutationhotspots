/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.loader.UniprotProxySequenceReader;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;

/**
 *
 * @author jgao
 */
public class MutatedProteinImpl implements MutatedProtein {
    private String gene;
    private String uniprotAcc;
    private int proteinLength;
    private List<Mutation> mutations;
    
    public MutatedProteinImpl(MutatedProtein protein) {
        this(protein.getGene(), protein.getUniprotAcc());
        proteinLength = protein.getProteinLength();
        setMutations(protein.getMutations());
    }

    public MutatedProteinImpl(String gene, String uniprotAcc) {
        this.gene = gene;
        this.uniprotAcc = uniprotAcc;
        mutations = new ArrayList<>();
        proteinLength = 0;
    }

    @Override
    public final String getGene() {
        return gene;
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
        for (int i=0; i<3 || proteinLength>0; i++) { // try 3 times
            proteinLength = getProteinLengthFromUniprot(uniprotAcc);
        }
        
        if (proteinLength==0) {
            System.err.println("Could not retrive length of protein "+uniprotAcc);
        }
        
        return proteinLength;
    }
    
    
    
    private static HttpClient httpClient;
    static {
        int timeOut = 5000;
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(10);
        connectionManager.getParams().setConnectionTimeout(timeOut);
        HttpClientParams params = new HttpClientParams();
        params.setIntParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, timeOut);
        httpClient = new HttpClient(params, connectionManager);
    }
    
    private static int getProteinLengthFromUniprot(String uniprotAcc) {
        String strURL = "http://www.uniprot.org/uniprot/"+uniprotAcc+".fasta";
        GetMethod method = new GetMethod(strURL);

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedReader bufReader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream()));
                String line = bufReader.readLine();
                if (line==null||!line.startsWith(">")) {
                    return 0;
                }

                int len = 0;
                for (line=bufReader.readLine(); line!=null; line=bufReader.readLine()) {
                    len += line.length();
                }
                return len;
            } else {
                //  Otherwise, throw HTTP Exception Object
                throw new HttpException(statusCode + ": " + HttpStatus.getStatusText(statusCode)
                        + " Base URL:  " + strURL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            //  Must release connection back to Apache Commons Connection Pool
            method.releaseConnection();
        }
    }

    @Override
    public final List<Mutation> getMutations() {
        return mutations;
    }

    @Override
    public final void setMutations(List<Mutation> mutations) {
        this.mutations = mutations;
    }

    private static Map<String, String> uniprotSequences = new HashMap<String, String>();
    
    @Override
    public String getProteinSequence() {
        if (!uniprotSequences.containsKey(uniprotAcc)) {
            String seq = null;
            try {
                UniprotProxySequenceReader<AminoAcidCompound> uniprotSequence
                        = new UniprotProxySequenceReader<AminoAcidCompound>(uniprotAcc,
                                AminoAcidCompoundSet.getAminoAcidCompoundSet());
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
        if (uniprotAcc!=null) {
            sb.append("_").append(uniprotAcc);
        }
        return sb.toString();
    }
    
    
}