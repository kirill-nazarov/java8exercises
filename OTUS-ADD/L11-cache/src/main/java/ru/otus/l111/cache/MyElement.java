package ru.otus.l111.cache;

import java.lang.ref.SoftReference;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class MyElement<K, V> {
    private final K key;
    private final SoftReference<V> value;
    private final long creationTime;
    private long lastAccessTime;


    public MyElement(K key, V value) {
        this.key = key;
        this.value = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public SoftReference<V> getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
