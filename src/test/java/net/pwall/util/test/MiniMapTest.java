/*
 * @(#) MiniMapTest.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2022 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.pwall.util.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.MiniMap;
import net.pwall.util.MiniMap0;
import net.pwall.util.MiniMap1;
import net.pwall.util.MiniMap2;
import net.pwall.util.MiniMap3;

public class MiniMapTest {

    @Test
    public void shouldCreateMiniMapOfSize0() {
        Map<String, Integer> miniMap = MiniMap.of();
        assertEquals(0, miniMap.size());
        assertFalse(miniMap.containsKey("abc"));
        assertFalse(miniMap.containsValue(123));
        assertNull(miniMap.get("abc"));
        assertThrows(UnsupportedOperationException.class, () -> miniMap.put("abc", 123));
        Iterator<String> keys = miniMap.keySet().iterator();
        assertFalse(keys.hasNext());
        Iterator<Integer> values = miniMap.values().iterator();
        assertFalse(values.hasNext());
        Iterator<Map.Entry<String, Integer>> entries = miniMap.entrySet().iterator();
        assertFalse(entries.hasNext());
        HashMap<String, Integer> hashMap = new HashMap<>();
        assertEquals(miniMap, hashMap);
        assertEquals(miniMap.hashCode(), hashMap.hashCode());
        assertEquals("{}", miniMap.toString());
    }

    @Test
    public void shouldCreateMiniMapOfSize1() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123);
        assertEquals(1, miniMap.size());
        assertTrue(miniMap.containsKey("abc"));
        assertFalse(miniMap.containsKey("xyz"));
        assertTrue(miniMap.containsValue(123));
        assertFalse(miniMap.containsValue(456));
        assertEquals(123, miniMap.get("abc"));
        assertThrows(UnsupportedOperationException.class, () -> miniMap.put("xyz", 456));
        Iterator<String> keys = miniMap.keySet().iterator();
        assertTrue(keys.hasNext());
        assertEquals("abc", keys.next());
        assertFalse(keys.hasNext());
        Iterator<Integer> values = miniMap.values().iterator();
        assertTrue(values.hasNext());
        assertEquals(123, values.next());
        assertFalse(values.hasNext());
        Iterator<Map.Entry<String, Integer>> entries = miniMap.entrySet().iterator();
        assertTrue(entries.hasNext());
        Map.Entry<String, Integer> entry = entries.next();
        assertEquals("abc", entry.getKey());
        assertEquals(123, entry.getValue());
        assertFalse(entries.hasNext());
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("abc", 123);
        assertEquals(miniMap, hashMap);
        assertEquals(miniMap.hashCode(), hashMap.hashCode());
        assertEquals("{abc=123}", miniMap.toString());
    }

    @Test
    public void shouldCreateMiniMapOfSize2() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123, "def", 888);
        assertEquals(2, miniMap.size());
        assertTrue(miniMap.containsKey("abc"));
        assertTrue(miniMap.containsKey("def"));
        assertFalse(miniMap.containsKey("xyz"));
        assertTrue(miniMap.containsValue(123));
        assertTrue(miniMap.containsValue(888));
        assertFalse(miniMap.containsValue(456));
        assertEquals(123, miniMap.get("abc"));
        assertEquals(888, miniMap.get("def"));
        assertThrows(UnsupportedOperationException.class, () -> miniMap.put("xyz", 456));
        Iterator<String> keys = miniMap.keySet().iterator();
        assertTrue(keys.hasNext());
        assertEquals("abc", keys.next());
        assertTrue(keys.hasNext());
        assertEquals("def", keys.next());
        assertFalse(keys.hasNext());
        Iterator<Integer> values = miniMap.values().iterator();
        assertTrue(values.hasNext());
        assertEquals(123, values.next());
        assertTrue(values.hasNext());
        assertEquals(888, values.next());
        assertFalse(values.hasNext());
        Iterator<Map.Entry<String, Integer>> entries = miniMap.entrySet().iterator();
        assertTrue(entries.hasNext());
        Map.Entry<String, Integer> entry = entries.next();
        assertEquals("abc", entry.getKey());
        assertEquals(123, entry.getValue());
        assertTrue(entries.hasNext());
        entry = entries.next();
        assertEquals("def", entry.getKey());
        assertEquals(888, entry.getValue());
        assertFalse(entries.hasNext());
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("abc", 123);
        hashMap.put("def", 888);
        assertEquals(miniMap, hashMap);
        assertEquals(miniMap.hashCode(), hashMap.hashCode());
        assertEquals("{abc=123, def=888}", miniMap.toString());
    }

    @Test
    public void shouldCreateMiniMapOfSize3() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123, "def", 888, "ghi", 27);
        assertEquals(3, miniMap.size());
        assertTrue(miniMap.containsKey("abc"));
        assertTrue(miniMap.containsKey("def"));
        assertTrue(miniMap.containsKey("ghi"));
        assertFalse(miniMap.containsKey("xyz"));
        assertTrue(miniMap.containsValue(123));
        assertTrue(miniMap.containsValue(888));
        assertTrue(miniMap.containsValue(27));
        assertFalse(miniMap.containsValue(456));
        assertEquals(123, miniMap.get("abc"));
        assertEquals(27, miniMap.get("ghi"));
        assertEquals(888, miniMap.get("def"));
        assertThrows(UnsupportedOperationException.class, () -> miniMap.put("xyz", 456));
        Iterator<String> keys = miniMap.keySet().iterator();
        assertTrue(keys.hasNext());
        assertEquals("abc", keys.next());
        assertTrue(keys.hasNext());
        assertEquals("def", keys.next());
        assertTrue(keys.hasNext());
        assertEquals("ghi", keys.next());
        assertFalse(keys.hasNext());
        Iterator<Integer> values = miniMap.values().iterator();
        assertTrue(values.hasNext());
        assertEquals(123, values.next());
        assertTrue(values.hasNext());
        assertEquals(888, values.next());
        assertTrue(values.hasNext());
        assertEquals(27, values.next());
        assertFalse(values.hasNext());
        Iterator<Map.Entry<String, Integer>> entries = miniMap.entrySet().iterator();
        assertTrue(entries.hasNext());
        Map.Entry<String, Integer> entry = entries.next();
        assertEquals("abc", entry.getKey());
        assertEquals(123, entry.getValue());
        assertTrue(entries.hasNext());
        entry = entries.next();
        assertEquals("def", entry.getKey());
        assertEquals(888, entry.getValue());
        assertTrue(entries.hasNext());
        entry = entries.next();
        assertEquals("ghi", entry.getKey());
        assertEquals(27, entry.getValue());
        assertFalse(entries.hasNext());
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("abc", 123);
        hashMap.put("def", 888);
        hashMap.put("ghi", 27);
        assertEquals(miniMap, hashMap);
        assertEquals(miniMap.hashCode(), hashMap.hashCode());
        assertEquals("{abc=123, def=888, ghi=27}", miniMap.toString());
    }

    @Test
    public void shouldCopyMiniMap0() {
        Map<String, Integer> miniMap = MiniMap.of();
        assertEquals(0, miniMap.size());
        Map<String, Integer> copy = new MiniMap0<>(miniMap);
        assertEquals(0, copy.size());
    }

    @Test
    public void shouldCopyMiniMap1() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123);
        assertEquals(1, miniMap.size());
        assertEquals(123, miniMap.get("abc"));
        Map<String, Integer> copy = new MiniMap1<>(miniMap);
        assertEquals(1, copy.size());
        assertEquals(123, copy.get("abc"));
    }

    @Test
    public void shouldCopyMiniMap2() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123, "def", 888);
        assertEquals(2, miniMap.size());
        assertEquals(123, miniMap.get("abc"));
        assertEquals(888, miniMap.get("def"));
        Map<String, Integer> copy = new MiniMap2<>(miniMap);
        assertEquals(2, copy.size());
        assertEquals(123, copy.get("abc"));
        assertEquals(888, copy.get("def"));
    }

    @Test
    public void shouldCopyMiniMap3() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123, "def", 888, "ghi", 27);
        assertEquals(3, miniMap.size());
        assertEquals(123, miniMap.get("abc"));
        assertEquals(888, miniMap.get("def"));
        assertEquals(27, miniMap.get("ghi"));
        Map<String, Integer> copy = new MiniMap3<>(miniMap);
        assertEquals(3, copy.size());
        assertEquals(123, copy.get("abc"));
        assertEquals(888, copy.get("def"));
        assertEquals(27, copy.get("ghi"));
    }

    @Test
    public void shouldRejectCopyOfWrongSize() {
        Map<String, Integer> miniMap = MiniMap.map("abc", 123);
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MiniMap0<>(miniMap));
        assertEquals("MiniMap0 size must be 0", e.getMessage());
        Map<String, Integer> miniMap0 = new MiniMap0<>();
        e = assertThrows(IllegalArgumentException.class, () -> new MiniMap1<>(miniMap0));
        assertEquals("MiniMap1 size must be 1", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniMap2<>(miniMap0));
        assertEquals("MiniMap2 size must be 2", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniMap3<>(miniMap0));
        assertEquals("MiniMap3 size must be 3", e.getMessage());
    }

}
