package org.scud.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 160;


    private Object[] table = new Object[tableSize];
    private HashSet<K> keys = new HashSet<>();

    @Override
    public int size() {
        int size = 0;
        for (Object o : table) {
            if (o != null) {
                size++;
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return table[key.hashCode() % tableSize] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (K k : keys) {
            if (get(k).equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        return (V) table[key.hashCode() % tableSize];
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(Object key, Object value) {
        table[key.hashCode() % tableSize] = value;
        keys.add((K) key);
        return (V) value;
    }//add check

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        Object o = table[key.hashCode() % tableSize];
        table[key.hashCode() % tableSize] = null;
        //noinspection SuspiciousMethodCalls
        keys.remove(key);
        return (V) o;
    }

    @Override
    public void putAll(Map m) {
        for (Object o : m.keySet()) {
            put(o, m.get(o));
        }
    }

    @Override
    public void clear() {
        table = new Object[tableSize];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keySet() {
        return (Set<K>) keys.clone();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Collection<V> values() {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
