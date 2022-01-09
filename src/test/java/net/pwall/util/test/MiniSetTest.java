/*
 * @(#) MiniSetTest.java
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
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.MiniSet;

public class MiniSetTest {

    @Test
    public void shouldCreateMiniSetOfSize0() {
        Set<String> miniSet = MiniSet.of();
        assertEquals(0, miniSet.size());
        assertFalse(miniSet.contains("anything"));
        assertEquals("[]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("one"));
    }

    @Test
    public void shouldCreateMiniSetOfSize1() {
        Set<String> miniSet = MiniSet.of("one");
        assertEquals(1, miniSet.size());
        assertTrue(miniSet.contains("one"));
        assertFalse(miniSet.contains("anything"));
        assertEquals("[one]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertTrue(iter.hasNext());
        assertEquals("one", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("two"));
    }

    @Test
    public void shouldCreateMiniSetOfSize2() {
        Set<String> miniSet = MiniSet.of("one", "two");
        assertEquals(2, miniSet.size());
        assertTrue(miniSet.contains("one"));
        assertTrue(miniSet.contains("two"));
        assertFalse(miniSet.contains("anything"));
        assertEquals("[one, two]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertTrue(iter.hasNext());
        assertEquals("one", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("two", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("three"));
    }

}
