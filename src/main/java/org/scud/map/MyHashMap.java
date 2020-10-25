package org.scud.map;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 16;


    private KeyValue[] table = new KeyValue[tableSize];

    private class KeyValue{
        Object key;
        Object value;

        public KeyValue(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap(){
    }

    public MyHashMap(int initialSize){
        tableSize = initialSize;
        table = new KeyValue[tableSize];
    }

    private void expand() {
        Object[] oldTable = table;
        int oldTableSize = tableSize;
        tableSize *= 2;
        table = new Object[tableSize];
        for (K key : keys) {
            table[key.hashCode() % tableSize] = oldTable[key.hashCode() % oldTableSize];
        }
    }

    @Override
    public int size() {
        return keys.size();
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
        while (table[key.hashCode() % tableSize] != null) {
            expand();
        }
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
            while (table[o.hashCode() % tableSize] != null) {
                expand();
            }
            put(o, m.get(o));
        }
    }

    @Override
    public void clear() {
        table = new Object[tableSize];
        keys.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keySet() {
        return (Set<K>) keys.clone();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values() {
        ArrayList<V> al = new ArrayList<>();
        for (Object value : table) {
            if (value != null) {
                al.add((V) value);
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
