/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection;

import java.util.Set;

/**
 *
 * @author jgao
 */
public interface HotspotDetective {
        
    void setParameters(HotspotDetectiveParameters parameters) throws HotspotException;
    
    Set<Hotspot> detectHotspots(MutatedProtein mutatedProtein) throws HotspotException;

}
