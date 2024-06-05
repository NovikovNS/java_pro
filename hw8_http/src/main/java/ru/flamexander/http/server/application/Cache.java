package ru.flamexander.http.server.application;

import java.util.Map;
import java.util.WeakHashMap;

public class Cache {
    public static Map<String, byte[]> getCacheMap() {
        return Map.copyOf(cacheMap);
    }

    private static Map<String, byte[]> cacheMap;

    public static void init() {
        cacheMap = new WeakHashMap<>();
        System.out.println("Кеш проинициализирован");
    }

    public static void addCache(String uri, byte[] bytes) {
        cacheMap.put(uri, bytes);
    }
}
