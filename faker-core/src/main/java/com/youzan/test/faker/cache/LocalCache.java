package com.youzan.test.faker.cache;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by libaixian on 16/8/3.
 */
public class LocalCache<T> {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Map<String, T> cache = new HashMap<>();

    public T getValue(String key) {
        readWriteLock.readLock().lock();
        T retValue = cache.get(key);
        readWriteLock.readLock().unlock();

        return retValue;
    }

    public Set<String> getKeySet() {
        readWriteLock.readLock().lock();
        Set<String> retValue = cache.keySet();
        readWriteLock.readLock().unlock();

        return retValue;
    }

    public void setValue(String key, T value) {
        readWriteLock.writeLock().lock();
        cache.put(key, value);
        readWriteLock.writeLock().unlock();
    }

    public void remove(String key) {
        readWriteLock.writeLock().lock();
        cache.remove(key);
        readWriteLock.writeLock().unlock();
    }
}
