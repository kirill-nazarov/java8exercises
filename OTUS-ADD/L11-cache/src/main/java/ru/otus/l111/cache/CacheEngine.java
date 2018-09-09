package ru.otus.l111.cache;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
