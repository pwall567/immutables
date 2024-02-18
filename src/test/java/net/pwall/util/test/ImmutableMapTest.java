/*
 * @(#) ImmutableMapTest.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2022, 2023, 2024 Peter Wall
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import net.pwall.util.ImmutableMap;
import net.pwall.util.ImmutableMapEntry;

public class ImmutableMapTest {

    @Test
    public void shouldCreateImmutableMap() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        assertTrue(ImmutableMap.containsKey(list, "alpha"));
        assertFalse(ImmutableMap.containsKey(list, "delta"));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        assertEquals(3, map.size());
        assertEquals(new Integer(123), map.get("alpha"));
        assertEquals(new Integer(456), map.get("beta"));
        assertEquals(new Integer(789), map.get("gamma"));
        assertEquals("{alpha=123, beta=456, gamma=789}", map.toString());
    }

    @Test
    public void shouldCreateImmutableMapFromArray() {
        ImmutableMapEntry<String, Integer>[] array = ImmutableMap.createArray(4);
        array[0] = ImmutableMap.entry("alpha", 123);
        array[1] = ImmutableMap.entry("beta", 456);
        array[2] = ImmutableMap.entry("gamma", 789);
        assertTrue(ImmutableMap.containsKey(array, 3, "alpha"));
        assertFalse(ImmutableMap.containsKey(array, 3, "delta"));
        ImmutableMap<String, Integer> map = new ImmutableMap<>(array, 3);
        assertEquals(3, map.size());
        assertEquals(new Integer(123), map.get("alpha"));
        assertEquals(new Integer(456), map.get("beta"));
        assertEquals(new Integer(789), map.get("gamma"));
    }

    @Test
    public void shouldWorkWithNullValues() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(ImmutableMap.entry("alpha", 123));
        list.add(ImmutableMap.entry("beta", 456));
        list.add(ImmutableMap.entry(null, 888));
        list.add(ImmutableMap.entry("gamma", null));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        assertEquals(4, map.size());
        assertEquals(new Integer(123), map.get("alpha"));
        assertEquals(new Integer(456), map.get("beta"));
        assertEquals(new Integer(888), map.get(null));
        assertTrue(map.containsKey("gamma"));
        assertNull(map.get("gamma"));
        assertEquals("{alpha=123, beta=456, null=888, gamma=null}", map.toString());
    }

    @Test
    public void shouldCompareWithADifferentMap() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("alpha", 123);
        hashMap.put("beta", 456);
        hashMap.put("gamma", 789);
        assertEquals(map, hashMap);
        assertEquals(hashMap, map);
        assertEquals(map.hashCode(), hashMap.hashCode());
        assertEquals(map.keySet(), hashMap.keySet());
        assertEquals(hashMap.keySet(), map.keySet());
        assertEquals(map.keySet().hashCode(), hashMap.keySet().hashCode());
        hashMap.put("gamma", 987);
        assertNotEquals(map, hashMap);
        assertNotEquals(hashMap, map);
    }

    @Test
    public void shouldCreateKeySet() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        assertTrue(keySet.contains("alpha"));
        assertTrue(keySet.contains("beta"));
        assertTrue(keySet.contains("gamma"));
        Iterator<String> iterator = keySet.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("alpha", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("beta", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("gamma", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldCreateValueCollection() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        Collection<Integer> values = map.values();
        assertEquals(3, values.size());
        assertTrue(values.contains(123));
        assertTrue(values.contains(456));
        assertTrue(values.contains(789));
        Iterator<Integer> iterator = values.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Integer(123), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Integer(456), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Integer(789), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldRejectMutatingOperations() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        assertThrows(UnsupportedOperationException.class, () -> map.put("delta", 888));
    }

    @Test
    public void shouldCreateEmptyMap() {
        ImmutableMap<String, Integer> empty = ImmutableMap.emptyMap();
        assertTrue(empty.isEmpty());
        Set<Map.Entry<String, Integer>> entrySet = empty.entrySet();
        Iterator<Map.Entry<String, Integer>> entryIterator = entrySet.iterator();
        assertFalse(entryIterator.hasNext());
        Set<String> keySet = empty.keySet();
        Iterator<String> keyIterator = keySet.iterator();
        assertFalse(keyIterator.hasNext());
        Collection<Integer> values = empty.values();
        Iterator<Integer> valueIterator = values.iterator();
        assertFalse(valueIterator.hasNext());
        assertFalse(empty.containsKey("alpha"));
        assertFalse(empty.containsValue(456));
        assertNull(empty.get("gamma"));
    }

    @Test
    public void shouldConditionallyCreateEmptyMap() {
        ImmutableMapEntry<String, Integer>[] array = ImmutableMap.createArray(1);
        array[0] = ImmutableMap.entry("omega", 999);
        ImmutableMap<String, Integer> map1 = ImmutableMap.mapOf(array, 0);
        assertTrue(map1.isEmpty());
        Set<Map.Entry<String, Integer>> entrySet1 = map1.entrySet();
        Iterator<Map.Entry<String, Integer>> entryIterator1 = entrySet1.iterator();
        assertFalse(entryIterator1.hasNext());
        Set<String> keySet1 = map1.keySet();
        Iterator<String> keyIterator1 = keySet1.iterator();
        assertFalse(keyIterator1.hasNext());
        Collection<Integer> values1 = map1.values();
        Iterator<Integer> valueIterator1 = values1.iterator();
        assertFalse(valueIterator1.hasNext());
        assertFalse(map1.containsKey("omega"));
        assertFalse(map1.containsValue(999));
        assertNull(map1.get("omega"));
        ImmutableMap<String, Integer> map2 = ImmutableMap.mapOf(array);
        assertEquals(1, map2.size());
        Set<Map.Entry<String, Integer>> entrySet2 = map2.entrySet();
        Iterator<Map.Entry<String, Integer>> entryIterator2 = entrySet2.iterator();
        assertTrue(entryIterator2.hasNext());
        assertEquals(ImmutableMap.entry("omega", 999), entryIterator2.next());
        assertFalse(entryIterator2.hasNext());
        Set<String> keySet2 = map2.keySet();
        Iterator<String> keyIterator2 = keySet2.iterator();
        assertTrue(keyIterator2.hasNext());
        assertEquals("omega", keyIterator2.next());
        assertFalse(keyIterator2.hasNext());
        Collection<Integer> values2 = map2.values();
        Iterator<Integer> valueIterator2 = values2.iterator();
        assertTrue(valueIterator2.hasNext());
        assertEquals(new Integer(999), valueIterator2.next());
        assertFalse(valueIterator2.hasNext());
        assertTrue(map2.containsKey("omega"));
        assertTrue(map2.containsValue(999));
        assertEquals(new Integer(999), map2.get("omega"));
        ImmutableMap<String, Integer> map3 = ImmutableMap.mapOf(array, 1);
        assertEquals(1, map3.size());
        assertTrue(map3.containsKey("omega"));
        assertTrue(map3.containsValue(999));
        assertEquals(new Integer(999), map3.get("omega"));
    }

    @Test
    public void shouldRejectAttemptToCreateMapWithIncorrectLength() {
        ImmutableMapEntry<String, Integer>[] array = ImmutableMap.createArray(1);
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableMap<>(array, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableMap<>(array, 2));
    }

    @Test
    public void shouldAllowIndexedAccessToKeySet() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        ImmutableMap.KeySet<String, Integer> keySet = map.keySet();
        assertEquals("alpha", keySet.get(0));
        assertEquals("beta", keySet.get(1));
        assertEquals("gamma", keySet.get(2));
    }

    @Test
    public void shouldAllowIndexedAccessToValueCollection() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        ImmutableMap.ValueCollection<String, Integer> values = map.values();
        assertEquals(new Integer(123), values.get(0));
        assertEquals(new Integer(456), values.get(1));
        assertEquals(new Integer(789), values.get(2));
    }

    @Test
    public void shouldCopyMap() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        ImmutableMap<String, Integer> otherMap = new ImmutableMap<>(map);
        assertEquals(map, otherMap);
        assertEquals(otherMap, map);
        assertEquals(map.hashCode(), otherMap.hashCode());
    }

    @Test
    public void shouldAllowIndexedAccessToMapEntries() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        ImmutableMapEntry<String, Integer> entry0 = map.getEntry(0);
        assertEquals("alpha", entry0.getKey());
        assertEquals(new Integer(123), entry0.getValue());
        ImmutableMapEntry<String, Integer> entry1 = map.getEntry(1);
        assertEquals("beta", entry1.getKey());
        assertEquals(new Integer(456), entry1.getValue());
        ImmutableMapEntry<String, Integer> entry2 = map.getEntry(2);
        assertEquals("gamma", entry2.getKey());
        assertEquals(new Integer(789), entry2.getValue());
    }

    @Test
    public void shouldAllowIndexedAccessToKeys() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        assertEquals("alpha", map.getKey(0));
        assertEquals("beta", map.getKey(1));
        assertEquals("gamma", map.getKey(2));
    }

    @Test
    public void shouldAllowIndexedAccessToValues() {
        List<ImmutableMapEntry<String, Integer>> list = new ArrayList<>();
        list.add(new ImmutableMapEntry<>("alpha", 123));
        list.add(new ImmutableMapEntry<>("beta", 456));
        list.add(new ImmutableMapEntry<>("gamma", 789));
        ImmutableMap<String, Integer> map = ImmutableMap.from(list);
        assertEquals(new Integer(123), map.getValue(0));
        assertEquals(new Integer(456), map.getValue(1));
        assertEquals(new Integer(789), map.getValue(2));
    }

}
