
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author jgao
 */
public interface Hotspot {
   
    /**
     * Merge with another hotspot
     * @param hotspot  another hotspot
     */
    public void mergeHotspot(Hotspot hotspot);
    
    /**
     * 
     * @return gene
     */
    public MutatedProtein getProtein();
    
    /**
     * Not only residues that have mutations, but also residues within the cluster.
     * @return residues
     */
    public SortedSet<Integer> getResidues();
    
    /**
     * 
     * @return a list of mutations
     */
    public Set<Mutation> getMutations();
    
    /**
     * Add a sample with aa change
     * @param mutation a mutation
     */
    public void addMutation(Mutation mutation);
    
    /**
     * 
     * @return samples mutated in this hotspot
     */
    public Set<String> getPatients();
    
    /**
     * 
     * @return 
     */
    public void setPValue(double pValue);
    
    /**
     * 
     * @return 
     */
    public double getPValue();
    
    /**
     * 
     * @return 
     */
    public String getLabel();
    
    public void setLabel(String label);
}
