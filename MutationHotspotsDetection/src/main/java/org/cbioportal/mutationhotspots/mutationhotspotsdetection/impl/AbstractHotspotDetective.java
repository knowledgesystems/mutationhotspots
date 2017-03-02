/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Hotspot;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotDetective;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotDetectiveParameters;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.HotspotException;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.MutatedProtein;
import org.cbioportal.mutationhotspots.mutationhotspotsdetection.Mutation;

/**
 *
 * @author jgao
 */
public abstract class AbstractHotspotDetective implements HotspotDetective {
    protected HotspotDetectiveParameters parameters;
    
    private Set<Hotspot> hotspots;
    protected int numberOfsequencedCases;
    protected Map<MutatedProtein, Integer> numberOfAllMutationOnProteins = new HashMap<MutatedProtein, Integer>();

    public AbstractHotspotDetective(HotspotDetectiveParameters parameters) throws HotspotException {
        setParameters(parameters);
    }

    @Override
    public final void setParameters(HotspotDetectiveParameters parameters) throws HotspotException {
        this.parameters = parameters;
//        numberOfsequencedCases = getNumberOfSequencedCases();
    }

    /**
     * 
     * @param hotspots
     * @return 
     */
    protected abstract Map<MutatedProtein, Set<Hotspot>> processSingleHotspotsOnAProtein(MutatedProtein protein, Map<Integer, Hotspot> mapResidueHotspot) throws HotspotException;
   
    private Map<Integer, Hotspot> getSingleHotspots(MutatedProtein protein) {
        Map<Integer, Hotspot> map = new HashMap<>();
        List<Mutation> mutations = protein.getMutations();
        Collection<String> mutationTypesFilter = parameters.getMutationTypes();
        for (Mutation m : mutations) {
            if (mutationTypesFilter!=null
                    && mutationTypesFilter.isEmpty()
                    && !mutationTypesFilter.contains(m.getMutationType())) {
                continue;
            }
            
            int start = m.getProteinStart();
            int end = m.getProteinEnd();
            for (int res=start; res<=end; res++) {
                Hotspot hotspot = map.get(res);
                if (hotspot==null) {
                    hotspot = new HotspotImpl(protein, numberOfsequencedCases, new TreeSet<Integer>(Arrays.asList(res)));
                    map.put(res, hotspot);
                }
                hotspot.addMutation(m);
            }
        }
        return map;
    }
    
    @Override
    public Set<Hotspot> detectHotspots(MutatedProtein protein) throws HotspotException {
        Map<Integer, Hotspot> mapResidueHotspot = getSingleHotspots(protein);
        return recordHotspots(protein, mapResidueHotspot);
    }
    
    private void removeNonrecurrentHotspots(Map<Integer, Hotspot> mapResidueHotspot) {
        Iterator<Map.Entry<Integer, Hotspot>> it = mapResidueHotspot.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Hotspot> entry = it.next();
            if (entry.getValue().getPatients().size()<parameters.getPrefilterThresholdSamplesOnSingleResidue()) {
                it.remove();
            }
        }
    }
    
    private Set<Hotspot> recordHotspots(MutatedProtein protein, Map<Integer, Hotspot> mapResidueHotspot) throws HotspotException {
        if (parameters.getPrefilterThresholdSamplesOnSingleResidue()>1) {
            removeNonrecurrentHotspots(mapResidueHotspot);
        }


        // process all hotspots
        Map<MutatedProtein, Set<Hotspot>> mapHotspots = processSingleHotspotsOnAProtein(protein, mapResidueHotspot);
        mapHotspots.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((hotspotsOnAProtein) -> {
            //            if (!hotspotsOnAProtein.isEmpty()) {
            //                mutatedProtein.setNumberOfMutations(getNumberOfAllMutationOnProtein(hotspotsOnAProtein)); // only have to set once
            //            }
            double p = parameters.getPValueThreshold();
            if (p>0 && p<1) {
                for (Hotspot hotspot : hotspotsOnAProtein) {
                    if (hotspot.getPValue()<=p) {
                        hotspots.add(hotspot);
                    }
                }
            } else {
                hotspots.addAll(hotspotsOnAProtein);
            }
        });
        
        return hotspots;
    }
    
    protected int getNumberOfAllMutationOnProtein(Collection<Hotspot> hotspotsOnAProtein) {
        // default behavior for single and linear hotspots
        Set<Mutation> mutations = new HashSet<>();
        hotspotsOnAProtein.forEach((hotspot) -> {
            mutations.addAll(hotspot.getMutations());
        });
        return mutations.size();
    }
    
}