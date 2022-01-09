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

import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.MiniMap;

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
    }

}
