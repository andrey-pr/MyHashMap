package org.scud.map;

import java.util.*;

import org.scud.list.MyLinkedList;

public class MyHashMap<K, V> implements Map<K, V> {
    private int tableSize = 16;
    private int sizePow = 4;
    private AutoRemover autoRemover;

    public MyHashMap() {
    }

    public MyHashMap(int entryTimeToLive) {
        autoRemover = new AutoRemover(this, entryTimeToLive);
    }

    @SuppressWarnings("unchecked")
    private MyLinkedList<Entry<K, V>>[] table = new MyLinkedList[tableSize];

    private static class MyEntry<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object setValue(Object value) {
            this.value = (V) value;
            return value;
        }
    }

    @SuppressWarnings("rawtypes")
    static class AutoRemover extends Thread {
        MyHashMap map;
        int timeout;
        Queue<Ticket> queue = new LinkedList<>();
        static class Ticket{
            long timeToDie;
            Object key;

            public Ticket(long timeToDie, Object key) {
                this.timeToDie = timeToDie;
                this.key = key;
            }
        }

        public AutoRemover(MyHashMap map, int timeout) {
            this.map = map;
            this.timeout = timeout;
            start();
        }

        public void add(Object key){
            queue.add(new Ticket(System.currentTimeMillis() + timeout, key));
        }

        @Override
        public void run() {
            super.run();
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    //noinspection BusyWait
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (queue.peek() != null && queue.peek().timeToDie < System.currentTimeMillis()) {
                    //noinspection ConstantConditions
                    map.remove(queue.poll().key);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        if (size() > tableSize >> 1) {
            MyLinkedList<Entry<K, V>>[] oldTable = table;
            sizePow++;
            int oldTableSize = tableSize;
            tableSize = tableSize << 1;
            table = new MyLinkedList[tableSize];
            for (int i = 0; i < oldTableSize; i++) {
                if (oldTable[i] != null) {
                    for (int a = 0; a < oldTable[i].size(); a++) {
                        putUnchecked(oldTable[i].get(a).getKey(), oldTable[i].get(a).getValue());
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                size += table[i].size();
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
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                for (int a = 0; a < table[i].size(); a++) {
                    if (table[i].get(a).getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))] == null) {
            return null;
        }
        for (Entry<K, V> kv : table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))]) {
            if (kv.getKey().equals(key)) {
                return kv.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(Object key, Object value) {
        expand();
        if (autoRemover != null) {
            autoRemover.add(key);
        }
        return putUnchecked(key, value);
    }

    @SuppressWarnings("unchecked")
    private V putUnchecked(Object key, Object value) {
        if (table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))] == null) {
            table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))] = new MyLinkedList<>();
        }
        table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))]
                .add(new MyEntry<>((K) key, (V) value));
        return (V) value;
    }

    @Override
    public V remove(Object key) {
        MyLinkedList<Entry<K, V>> row = table[key.hashCode() & (0x7FFFFFFF >> (31 - sizePow))];
        if (row == null) {
            return null;
        }
        for (int a = 0; a < row.size(); a++) {
            if (row.get(a).getKey().equals(key)) {
                V v = row.get(a).getValue();
                row.remove(a);
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
        table = new MyLinkedList[tableSize];
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                for (int a = 0; a < table[i].size(); a++) {
                    keys.add(table[i].get(a).getKey());
                }
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> al = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                for (int a = 0; a < table[i].size(); a++) {
                    al.add(table[i].get(a).getValue());
                }
            }
        }
        return al;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                set.addAll(table[i]);
            }
        }
        return set;
    }
}
