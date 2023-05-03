package ru.otus.cache;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    Map<K, V> weakHashMap = new WeakHashMap<>();

    List<HwListener<K,V>> listeners;

    public MyCache() {
        this.listeners = new ArrayList<>();
    }


    @Override
    public void put(K key, V value) {
        if (!listeners.isEmpty()) {
            for(var l : listeners){
                l.notify(key, value, "put");
            }
        }
        weakHashMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        if (!listeners.isEmpty()) {
            for(var l : listeners){
                l.notify(key, weakHashMap.get(key), "remove");
            }
        }
        weakHashMap.remove(key);
    }

    @Override
    public V get(K key) {
        if (!listeners.isEmpty()) {
            for(var l : listeners){
                l.notify(key, weakHashMap.get(key), "get");
            }
        }
        return weakHashMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

}

