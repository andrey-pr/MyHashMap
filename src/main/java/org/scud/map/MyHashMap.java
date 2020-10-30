package org.scud.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 16;


    @SuppressWarnings("unchecked")
    private KeyValue<K, V>[][] table = new KeyValue[tableSize][0];

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
        table = new KeyValue[tableSize][0];
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        if (size() > tableSize / 2) {
            KeyValue<K, V>[][] oldTable = table;
            int oldTableSize = tableSize;
            tableSize *= 2;
            table = new KeyValue[tableSize][0];
            for (int i = 0; i < oldTableSize; i++) {
                for (int a = 0; a < oldTable[i].length; a++) {
                    putUnchecked(oldTable[i][a].key, oldTable[i][a].value);
                }
            }
        }
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < tableSize; i++) {
            size += table[i].length;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < tableSize; i++) {
            for (int a = 0; a < table[i].length; a++) {
                if (table[i][a].value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (int a = 0; a < table[key.hashCode() % tableSize].length; a++) {
            if (table[key.hashCode() % tableSize][a].key.equals(key)) {
                return table[key.hashCode() % tableSize][a].value;
            }
        }
        return null;
    }

    @Override
    public V put(Object key, Object value) {
        expand();
        return putUnchecked(key, value);
    }

    @SuppressWarnings("unchecked")
    private V putUnchecked(Object key, Object value) {
        table[key.hashCode() % tableSize] = addToArr(
                table[key.hashCode() % tableSize],
                new KeyValue<>((K) key, (V) value)
        );
        return (V) value;
    }

    @Override
    public V remove(Object key) {
        KeyValue<K, V>[] o = table[key.hashCode() % tableSize];
        for (int a = 0; a < o.length; a++) {
            if (o[a].key.equals(key)) {
                V v = o[a].value;
                table[key.hashCode() % tableSize] = deleteFromArr(o, a);
                return v;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map m) {
        for (Object o : m.keySet()) {
            put(o, m.get(o));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = new KeyValue[tableSize][0];
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < tableSize; i++) {
            for (int a = 0; a < table[i].length; a++) {
                keys.add(table[i][a].key);
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> al = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            for (int a = 0; a < table[i].length; a++) {
                al.add(table[i][a].value);
            }
        }
        return al;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @SuppressWarnings("unchecked")
    private KeyValue<K, V>[] deleteFromArr(KeyValue<K, V>[] arr, int index) {
        KeyValue<K, V>[] newArr = new KeyValue[arr.length - 1];
        for (int i = 0; i < arr.length; i++) {
            if (i < index) {
                newArr[i] = arr[i];
            } else if (i > index) {
                newArr[i - 1] = arr[i];
            }
        }
        return newArr;
    }

    @SuppressWarnings("unchecked")
    private KeyValue<K, V>[] addToArr(KeyValue<K, V>[] arr, KeyValue<K, V> o) {
        KeyValue<K, V>[] newArr = new KeyValue[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[newArr.length - 1] = o;
        return newArr;
    }
}
