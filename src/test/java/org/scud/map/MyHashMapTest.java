package org.scud.map;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MyHashMapTest {

    @Test
    public void size() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        assertEquals(0, map.size());
    }

    @Test
    public void sizeOne() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        assertEquals(1, map.size());
    }

    @Test
    public void sizeMany() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(3, map.size());
    }

    @Test
    public void isEmptyTrue() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        assertTrue(map.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        assertFalse(map.isEmpty());
    }

    @Test
    public void containsKeyTrue() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertTrue(map.containsKey("key"));
    }

    @Test
    public void containsKeyFalse() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertFalse(map.containsKey("key0"));
    }

    @Test
    public void containsValueTrue() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertTrue(map.containsValue("value"));
    }

    @Test
    public void containsValueFalse() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertFalse(map.containsValue("value0"));
    }

    @Test
    public void get() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals("value", map.get("key"));
    }

    @Test
    public void getNull() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertNull(map.get("key0"));
    }

    @Test
    public void put() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals("value", map.get("key"));
    }

    @Test
    public void remove() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.remove("key");
        assertNull(map.get("key"));
    }

    @Test
    public void putAll() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("key", "value");
        map2.put("key1", "value1");
        map2.put("key2", "value2");
        map.putAll(map2);
        assertEquals("value1", map.get("key1"));
    }

    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test
    public void clear() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.clear();
        assertNull(map.get("key"));
    }

    @Test
    public void keySet() {
        MyHashMap<String, String> map = new MyHashMap<String, String>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(new HashSet<String>(Arrays.asList("key", "key1", "key2")), map.keySet());
    }
}