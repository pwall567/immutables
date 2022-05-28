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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.MiniSet;
import net.pwall.util.MiniSet0;
import net.pwall.util.MiniSet1;
import net.pwall.util.MiniSet2;
import net.pwall.util.MiniSet3;
import net.pwall.util.MiniSet4;
import net.pwall.util.MiniSet5;

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
        HashSet<String> hashSet = new HashSet<>();
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
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
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("one");
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
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
        HashSet<String> hashSet = new HashSet<>(Arrays.asList("one", "two"));
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
    }

    @Test
    public void shouldCreateMiniSetOfSize3() {
        Set<String> miniSet = MiniSet.of("one", "two", "three");
        assertEquals(3, miniSet.size());
        assertTrue(miniSet.contains("one"));
        assertTrue(miniSet.contains("two"));
        assertTrue(miniSet.contains("three"));
        assertFalse(miniSet.contains("anything"));
        assertEquals("[one, two, three]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertTrue(iter.hasNext());
        assertEquals("one", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("two", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("three", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("four"));
        HashSet<String> hashSet = new HashSet<>(Arrays.asList("one", "two", "three"));
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
    }

    @Test
    public void shouldCreateMiniSetOfSize4() {
        Set<String> miniSet = MiniSet.of("one", "two", "three", "four");
        assertEquals(4, miniSet.size());
        assertTrue(miniSet.contains("one"));
        assertTrue(miniSet.contains("two"));
        assertTrue(miniSet.contains("three"));
        assertTrue(miniSet.contains("four"));
        assertFalse(miniSet.contains("anything"));
        assertEquals("[one, two, three, four]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertTrue(iter.hasNext());
        assertEquals("one", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("two", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("three", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("four", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("five"));
        HashSet<String> hashSet = new HashSet<>(Arrays.asList("one", "two", "three", "four"));
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
    }

    @Test
    public void shouldCreateMiniSetOfSize5() {
        Set<String> miniSet = MiniSet.of("one", "two", "three", "four", "five");
        assertEquals(5, miniSet.size());
        assertTrue(miniSet.contains("one"));
        assertTrue(miniSet.contains("two"));
        assertTrue(miniSet.contains("three"));
        assertTrue(miniSet.contains("four"));
        assertTrue(miniSet.contains("five"));
        assertFalse(miniSet.contains("anything"));
        assertEquals("[one, two, three, four, five]", miniSet.toString());
        Iterator<String> iter = miniSet.iterator();
        assertTrue(iter.hasNext());
        assertEquals("one", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("two", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("three", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("four", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("five", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(UnsupportedOperationException.class, () -> miniSet.add("six"));
        HashSet<String> hashSet = new HashSet<>(Arrays.asList("one", "two", "three", "four", "five"));
        assertEquals(miniSet, hashSet);
        assertEquals(hashSet, miniSet);
        assertEquals(miniSet.hashCode(), hashSet.hashCode());
    }

    @Test
    public void shouldCopyMiniSet0() {
        Set<String> miniSet = MiniSet.of();
        assertEquals(0, miniSet.size());
        Set<String> copy = new MiniSet0<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldCopyMiniSet1() {
        Set<String> miniSet = MiniSet.of("one");
        assertEquals(1, miniSet.size());
        Set<String> copy = new MiniSet1<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldCopyMiniSet2() {
        Set<String> miniSet = MiniSet.of("one", "two");
        assertEquals(2, miniSet.size());
        Set<String> copy = new MiniSet2<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldCopyMiniSet3() {
        Set<String> miniSet = MiniSet.of("one", "two", "three");
        assertEquals(3, miniSet.size());
        Set<String> copy = new MiniSet3<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldCopyMiniSet4() {
        Set<String> miniSet = MiniSet.of("one", "two", "three", "four");
        assertEquals(4, miniSet.size());
        Set<String> copy = new MiniSet4<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldCopyMiniSet5() {
        Set<String> miniSet = MiniSet.of("one", "two", "three", "four", "five");
        assertEquals(5, miniSet.size());
        Set<String> copy = new MiniSet5<>(miniSet);
        assertEquals(miniSet, copy);
    }

    @Test
    public void shouldRejectCopyOfWrongSize() {
        Set<String> miniSet = MiniSet.of("one");
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MiniSet0<>(miniSet));
        assertEquals("MiniSet0 size must be 0", e.getMessage());
        Set<String> miniSet0 = MiniSet.of();
        e = assertThrows(IllegalArgumentException.class, () -> new MiniSet1<>(miniSet0));
        assertEquals("MiniSet1 size must be 1", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniSet2<>(miniSet0));
        assertEquals("MiniSet2 size must be 2", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniSet3<>(miniSet0));
        assertEquals("MiniSet3 size must be 3", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniSet4<>(miniSet0));
        assertEquals("MiniSet4 size must be 4", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new MiniSet5<>(miniSet0));
        assertEquals("MiniSet5 size must be 5", e.getMessage());
    }

}
