package org.scud.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 16;
    private int sizePow = 4;


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
    private void expand() {
        if (size() > tableSize / 2) {
            KeyValue<K, V>[][] oldTable = table;
            sizePow++;
            int oldTableSize = tableSize;
            tableSize = tableSize << 2;
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
        for (KeyValue<K, V> kv : table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))]) {
            if (kv.key.equals(key)) {
                return kv.value;
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
        table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))] = addToArr(
                table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))],
                new KeyValue<>((K) key, (V) value)
        );
        return (V) value;
    }

    @Override
    public V remove(Object key) {
        KeyValue<K, V>[] row = table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))];
        for (int a = 0; a < row.length; a++) {
            if (row[a].key.equals(key)) {
                V v = row[a].value;
                table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))] = deleteFromArr(row, a);
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
        System.arraycopy(arr, 0, newArr, 0, index);
        System.arraycopy(arr, index + 1, newArr, index, arr.length - index - 1);
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
