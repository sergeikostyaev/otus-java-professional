package ru.otus.cache;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    Map<K, V> weakHashMap = new WeakHashMap<>();

    HwListener<K, V> hwListener;

    public static void main(String[] args) {
        MyCache<Integer, String> myCache = new MyCache<>();
        myCache.put(1,"1");
        System.out.println(myCache.get(1));

        var a = new WeakReference<Integer>(2);
        System.out.println(a.get());
    }

    @Override
    public void put(K key, V value) {
        if (hwListener != null) hwListener.notify(key, value, "put");
        weakHashMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        if (hwListener != null) hwListener.notify(key, weakHashMap.get(key), "remove");
        weakHashMap.remove(key);
    }

    @Override
    public V get(K key) {
        if (hwListener != null) hwListener.notify(key, weakHashMap.get(key), "get");
        return weakHashMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListener = listener;
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListener = null;
    }

    public Map<K, V> getWeakHashMap() {
        return weakHashMap;
    }
}

class A {

    int id;

    public A(int id) {
        this.id = id;
    }

    private static final Logger logger = LoggerFactory.getLogger(A.class);

    public static void main(String[] args) {

        MyCache<String, A> cache = new MyCache<>();
        var limit = 100000000;
        for (var idx = 0; idx < limit; idx++) {

            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;
            double usedMemoryPercent = (double) usedMemory / totalMemory * 100;
            System.out.println(usedMemoryPercent);



            var key = String.valueOf(idx); // при таком подходе значение удалится.
            cache.put(key, new A(idx));
        }

        System.out.println(cache.getWeakHashMap().size());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("after gc: {}", cache.getWeakHashMap().size());


















//        WeakHashMap<String, A> cache = new WeakHashMap<>();
//        var limit = 100;
//        for (var idx = 0; idx < 4; idx++) {
//
//            var key =  String.valueOf(idx); // при таком подходе значение удалится.
//            cache.put(key, new A(idx));
//        }
//
//
//        System.out.println(cache.entrySet().size());
//        for (Map.Entry<String, A> element : cache.entrySet()) {
//            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
//        }
//
//        System.gc();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        logger.info("after gc: {}", cache.size());
//
//
//
//        System.out.println(cache.entrySet().size());
//        System.out.println("----------------------------------------------------");
//        System.out.println("----------------------------------------------------");
//        System.out.println("----------------------------------------------------");
//        for (Map.Entry<String, A> element : cache.entrySet()) {
//            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
//        }


//        WeakHashMap<String, Integer> cache = new WeakHashMap<>();
//        var limit = 100;
//        for (var idx = 0; idx < 4; idx++) {
//
//            var key =  String.valueOf(idx); // при таком подходе значение удалится.
//            cache.put(key, idx);
//        }
//
//
//        System.out.println(cache.entrySet().size());
//        for (Map.Entry<String, Integer> element : cache.entrySet()) {
//            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
//        }
//
//        System.gc();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        logger.info("after gc: {}", cache.size());
//
//
//
//        System.out.println(cache.entrySet().size());
//        System.out.println("----------------------------------------------------");
//        System.out.println("----------------------------------------------------");
//        System.out.println("----------------------------------------------------");
//        for (Map.Entry<String, Integer> element : cache.entrySet()) {
//            logger.info("key:{}, value:{}", element.getKey(), element.getValue());
//        }


    }
}
