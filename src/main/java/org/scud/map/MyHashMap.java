package org.scud.map;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 16;


    @SuppressWarnings("unchecked")
    private KeyValue<K, V>[] table = new KeyValue[tableSize];

    private static class KeyValue<K, V> {
        K key;
        V value;

        public KeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialSize) {
        tableSize = initialSize;
        table = new KeyValue[tableSize];
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        KeyValue<K, V>[] oldTable = table;
        int oldTableSize = tableSize;
        tableSize *= 2;
        table = new KeyValue[tableSize];
        for (int i = 0; i < oldTableSize; i++) {
            if (oldTable[i] != null) {
                table[oldTable[i].key.hashCode() % tableSize] = oldTable[oldTable[i].key.hashCode() % oldTableSize];
            }
        }
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
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
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                if (table[i].value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (table[key.hashCode() % tableSize] != null) {
            return table[key.hashCode() % tableSize].value;
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(Object key, Object value) {
        while (table[key.hashCode() % tableSize] != null) {
            expand();
        }
        table[key.hashCode() % tableSize] = new KeyValue<>((K) key, (V) value);
        return (V) value;
    }

    @Override
    public V remove(Object key) {
        V o = table[key.hashCode() % tableSize].value;
        table[key.hashCode() % tableSize] = null;
        return o;
    }

    @Override
    public void putAll(Map m) {
        for (Object o : m.keySet()) {
            while (table[o.hashCode() % tableSize] != null) {
                expand();
            }
            put(o, m.get(o));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = new KeyValue[tableSize];
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (KeyValue<K, V> kv : table) {
            if (kv != null) {
                keys.add(kv.key);
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> al = new ArrayList<>();
        for (KeyValue<K, V> kv : table) {
            if (kv != null) {
                al.add(kv.value);
            }
        }
        return al;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
