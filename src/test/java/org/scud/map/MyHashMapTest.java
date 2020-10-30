package org.scud.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;

public class MyHashMapTest {

    @Test
    public void initialSize() {
        MyHashMap<String, String> map = new MyHashMap<>(20);
        for (int i = 0; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        assertEquals(20, map.size());
    }

    @Test
    public void resize() {
        MyHashMap<String, String> map = new MyHashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        assertEquals(20, map.size());
    }

    @Test
    public void resizeCorrectness() {
        MyHashMap<String, String> map = new MyHashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put("" + i, "" + i);
        }
        for (int i = 0; i < 20; i++) {
            if (!map.get("" + i).equals("" + i)) {
                fail();
            }
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    public void size() {
        MyHashMap<String, String> map = new MyHashMap<>();
        assertEquals(0, map.size());
    }

    @Test
    public void sizeOne() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        assertEquals(1, map.size());
    }

    @Test
    public void sizeMany() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(3, map.size());
    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "ConstantConditions"})
    @Test
    public void isEmptyTrue() {
        MyHashMap<String, String> map = new MyHashMap<>();
        assertTrue(map.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        assertFalse(map.isEmpty());
    }

    @Test
    public void containsKeyTrue() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertTrue(map.containsKey("key"));
    }

    @Test
    public void containsKeyFalse() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertFalse(map.containsKey("key0"));
    }

    @Test
    public void containsValueTrue() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertTrue(map.containsValue("value"));
    }

    @Test
    public void containsValueFalse() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertFalse(map.containsValue("value0"));
    }

    @Test
    public void get() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals("value", map.get("key"));
    }

    @Test
    public void getNull() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertNull(map.get("key0"));
    }

    @Test
    public void put() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals("value", map.get("key"));
    }

    @Test
    public void remove() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.remove("key");
        assertNull(map.get("key"));
    }

    @Test
    public void putAll() {
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("key", "value");
        map2.put("key1", "value1");
        map2.put("key2", "value2");
        MyHashMap<String, String> map = new MyHashMap<>();
        map.putAll(map2);
        assertEquals("value1", map.get("key1"));
    }

    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test
    public void clear() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.clear();
        assertNull(map.get("key"));
    }

    @Test
    public void keySet() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(new HashSet<>(Arrays.asList("key", "key1", "key2")), map.keySet());
    }

    @Test
    public void values() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        Collection<String> col = map.values();
        if (!col.contains("value") || !col.contains("value1") || !col.contains("value2")) {
            fail();
        }
    }
}