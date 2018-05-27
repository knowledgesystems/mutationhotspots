/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils.structure;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jgao
 */
public class OneToOneMap<K extends Comparable, V extends Comparable> {
        private Map<K, V> keyToValMap;
        private Map<V, K> valToKeyMap;
        
        OneToOneMap() {
            keyToValMap = new HashMap<K,V>();
            valToKeyMap = new HashMap<V,K>();
        }
        
        void put(K k, V v) {
            if (!keyToValMap.containsKey(k) && !valToKeyMap.containsKey(v)) {
                keyToValMap.put(k, v);
                valToKeyMap.put(v, k);
            }
        }
        
        int size() {
            return keyToValMap.size();
        }
        
        boolean hasKey(K k) {
            return keyToValMap.containsKey(k);
        }
        
        boolean hasValue(V v) {
            return valToKeyMap.containsKey(v);
        }
        
        V getByKey(K k) {
            return keyToValMap.get(k);
        }
        
        K getByValue(V v) {
            return valToKeyMap.get(v);
        }

        Set<K> getKeySet() {
            return keyToValMap.keySet();
        }

        Set<V> getValSet() {
            return valToKeyMap.keySet();
        }
        
        void retainByKey(Collection<K> keys) {
            keyToValMap.keySet().retainAll(keys);
            Iterator<Map.Entry<V,K>> itEntry = valToKeyMap.entrySet().iterator();
            while (itEntry.hasNext()) {
                Map.Entry<V,K> entry = itEntry.next();
                if (!keyToValMap.containsKey(entry.getValue())) {
                    itEntry.remove();
                }
            }
        }
        
        void retainByValue(Collection<V> values) {
            valToKeyMap.keySet().retainAll(values);
            Iterator<Map.Entry<K,V>> itEntry = keyToValMap.entrySet().iterator();
            while (itEntry.hasNext()) {
                Map.Entry<K,V> entry = itEntry.next();
                if (!valToKeyMap.containsKey(entry.getValue())) {
                    itEntry.remove();
                }
            }
        }
        
        K getSmallestKey() {
            return (K) Collections.min(keyToValMap.keySet());
        }
        
        K getLargestKey() {
            return (K) Collections.max(keyToValMap.keySet());
        }
        
        V getSmallestValue() {
            return (V) Collections.min(valToKeyMap.keySet());
        }
        
        V getLargestValue() {
            return (V) Collections.max(valToKeyMap.keySet());
        }
    }
