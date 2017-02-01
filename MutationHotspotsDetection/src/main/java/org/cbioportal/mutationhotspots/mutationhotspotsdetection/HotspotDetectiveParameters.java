/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.util.Collection;

/**
 *
 * @author jgao
 */
public class HotspotDetectiveParameters {
    private Collection<Integer> cancerStudyIds;
    private Collection<String> mutationTypes; // missense or truncating
    private Collection<Long> entrezGeneIds;
    private Collection<Long>  excludeEntrezGeneIds;
    private int thresholdHyperMutator = -1;
    private int thresholdSamples;
    private boolean seperateByProteinChangesForSingleResidueHotspot;
    private int linearSpotWindowSize;
    private int ptmHotspotWindowSize;
    private double distanceThresholdClosestAtomsFor3DHotspots;
    private double distanceThresholdCAlphaFor3DHotspots;
    private double distanceErrorThresholdFor3DHotspots;
    private double identpThresholdFor3DHotspots;
    private boolean includingMismatchesFor3DHotspots;
    private int prefilterThresholdSamplesOnSingleResidue;
    private boolean mergeOverlappingHotspots;
    private double pValueThreshold;
    private boolean calculatePValue;

    
    public Collection<Integer> getCancerStudyIds() {
        return cancerStudyIds;
    }

    
    public void setCancerStudyIds(Collection<Integer> cancerStudyIds) {
        this.cancerStudyIds = cancerStudyIds;
    }

    
    public Collection<String> getMutationTypes() {
        return mutationTypes;
    }

    
    public void setMutationTypes(Collection<String> mutationTypes) {
        this.mutationTypes = mutationTypes;
    }

    
    public Collection<Long> getEntrezGeneIds() {
        return entrezGeneIds;
    }

    
    public void setEntrezGeneIds(Collection<Long> entrezGeneIds) {
        this.entrezGeneIds = entrezGeneIds;
    }

    
    public Collection<Long> getExcludeEntrezGeneIds() {
        return excludeEntrezGeneIds;
    }

    
    public void setExcludeEntrezGeneIds(Collection<Long> excludeEntrezGeneIds) {
        this.excludeEntrezGeneIds = excludeEntrezGeneIds;
    }

    
    public int getThresholdHyperMutator() {
        return thresholdHyperMutator;
    }

    
    public void setThresholdHyperMutator(int thresholdHyperMutator) {
        this.thresholdHyperMutator = thresholdHyperMutator;
    }

    
    public int getThresholdSamples() {
        return thresholdSamples;
    }

    
    public void setThresholdSamples(int thresholdSamples) {
        this.thresholdSamples = thresholdSamples;
    }

    
    public int getLinearSpotWindowSize() {
        return linearSpotWindowSize;
    }

    
    public void setLinearSpotWindowSize(int linearSpotWindowSize) {
        this.linearSpotWindowSize = linearSpotWindowSize;
    }

    
    public double getDistanceClosestAtomsThresholdFor3DHotspots() {
        return distanceThresholdClosestAtomsFor3DHotspots;
    }

    
    public void setDistanceClosestAtomsThresholdFor3DHotspots(double anstrom) {
        this.distanceThresholdClosestAtomsFor3DHotspots = anstrom;
    }

    
    public double getDistanceCAlphaThresholdFor3DHotspots() {
        return distanceThresholdCAlphaFor3DHotspots;
    }

    
    public void setDistanceCAlphaThresholdFor3DHotspots(double anstrom) {
        this.distanceThresholdCAlphaFor3DHotspots = distanceThresholdCAlphaFor3DHotspots;
    }

    
    public double getDistanceErrorThresholdFor3DHotspots() {
        return distanceErrorThresholdFor3DHotspots;
    }

    
    public void setDistanceErrorThresholdFor3DHotspots(double distanceErrorThresholdFor3DHotspots) {
        this.distanceErrorThresholdFor3DHotspots = distanceErrorThresholdFor3DHotspots;
    }

    
    public double getIdentpThresholdFor3DHotspots() {
        return identpThresholdFor3DHotspots;
    }

    
    public void setIdentpThresholdFor3DHotspots(double identpThresholdFor3DHotspots) {
        this.identpThresholdFor3DHotspots = identpThresholdFor3DHotspots;
    }

    
    public boolean getIncludingMismatchesFor3DHotspots() {
        return includingMismatchesFor3DHotspots;
    }

    
    public void setIncludingMismatchesFor3DHotspots(boolean includeMismatches) {
        this.includingMismatchesFor3DHotspots = includeMismatches;
    }

    
    public boolean getSeperateByProteinChangesForSingleResidueHotspot() {
        return seperateByProteinChangesForSingleResidueHotspot;
    }

    
    public void setSeperateByProteinChangesForSingleResidueHotspot(boolean b) {
        this.seperateByProteinChangesForSingleResidueHotspot = b;
    }

    
    public int getPrefilterThresholdSamplesOnSingleResidue() {
        return prefilterThresholdSamplesOnSingleResidue;
    }

    
    public void setPrefilterThresholdSamplesOnSingleResidue(int prefilterThresholdSamplesOnSingleResidue) {
        this.prefilterThresholdSamplesOnSingleResidue = prefilterThresholdSamplesOnSingleResidue;
    }

    
    public boolean getMergeOverlappingHotspots() {
        return mergeOverlappingHotspots;
    }

    
    public void setMergeOverlappingHotspots(boolean mergeOverlappingHotspots) {
        this.mergeOverlappingHotspots = mergeOverlappingHotspots;
    }

    
    public void setPtmHotspotWindowSize(int ptmHotspotWindowSize) {
        this.ptmHotspotWindowSize = ptmHotspotWindowSize;
    }

    
    public int getPtmHotspotWindowSize() {
        return ptmHotspotWindowSize;
    }

    
    public double getPValueThreshold() {
        return pValueThreshold;
    }

    
    public void setPValueThreshold(double pValueThreshold) {
        this.pValueThreshold = pValueThreshold;
    }

    
    public boolean calculatePValue() {
        return calculatePValue;
    }

    
    public void setCalculatePValue(boolean calculatePValue) {
        this.calculatePValue = calculatePValue;
    }
}
