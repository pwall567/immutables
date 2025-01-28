/*
 * @(#) ImmutableSetTest.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2022, 2024 Peter Wall
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

package io.jstuff.util.test;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import io.jstuff.util.ImmutableSet;

public class ImmutableSetTest {

    @Test
    public void shouldCreateImmutableSet() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        assertEquals(3, set.size());
        assertTrue(set.contains("un"));
        assertTrue(set.contains("deux"));
        assertTrue(set.contains("trois"));
        assertEquals("[un, deux, trois]", set.toString());
    }

    @Test
    public void shouldWorkWithNullEntries() {
        String[] array = new String[] { "un", null, "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        assertEquals(4, set.size());
        assertTrue(set.contains("un"));
        assertTrue(set.contains(null));
        assertTrue(set.contains("deux"));
        assertTrue(set.contains("trois"));
        assertEquals("[un, null, deux, trois]", set.toString());
    }

    @Test
    public void shouldCompareWithADifferentSet() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("un");
        hashSet.add("deux");
        hashSet.add("trois");
        assertEquals(set, hashSet);
        assertEquals(hashSet, set);
        assertEquals(set.hashCode(), hashSet.hashCode());
    }

    @Test
    public void shouldCreateIterator() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        Iterator<String> iterator = set.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("un", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("deux", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("trois", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldRejectMutatingOperations() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        assertThrows(UnsupportedOperationException.class, () -> set.add("wrong"));
        assertThrows(UnsupportedOperationException.class, () -> set.remove("un"));
        assertThrows(UnsupportedOperationException.class, () -> set.addAll(new HashSet<>()));
        assertThrows(UnsupportedOperationException.class, () -> set.removeAll(new HashSet<String>()));
        assertThrows(UnsupportedOperationException.class, () -> set.retainAll(new HashSet<String>()));
        assertThrows(UnsupportedOperationException.class, set::clear);
        Iterator<String> iterator = set.iterator();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    public void shouldCreateEmptySet() {
        ImmutableSet<String> empty = ImmutableSet.emptySet();
        assertTrue(empty.isEmpty());
        Iterator<String> iterator = empty.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldConditionallyCreateEmptySet() {
        String[] array = new String[1];
        ImmutableSet<String> list1 = ImmutableSet.setOf(array, 0);
        assertTrue(list1.isEmpty());
        Iterator<String> iterator1 = list1.iterator();
        assertFalse(iterator1.hasNext());
        array[0] = "neuf";
        ImmutableSet<String> list2 = ImmutableSet.setOf(array);
        assertEquals(1, list2.size());
        Iterator<String> iterator2 = list2.iterator();
        assertTrue(iterator2.hasNext());
        assertEquals("neuf", iterator2.next());
        assertFalse(iterator2.hasNext());
        ImmutableSet<String> list3 = ImmutableSet.setOf(array, 1);
        assertEquals(1, list3.size());
        Iterator<String> iterator3 = list3.iterator();
        assertTrue(iterator3.hasNext());
        assertEquals("neuf", iterator3.next());
        assertFalse(iterator3.hasNext());
    }

    @Test
    public void shouldRejectAttemptToCreateSetWithIncorrectLength() {
        String[] array = new String[1];
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableSet<>(array, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableSet<>(array, 2));
    }

    @Test
    public void shouldAllowIndexedAccess() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set = new ImmutableSet<>(array);
        assertEquals("un", set.get(0));
        assertEquals("deux", set.get(1));
        assertEquals("trois", set.get(2));
    }

    @Test
    public void shouldCopyImmutableSet() {
        String[] array = new String[] { "un", "deux", "trois" };
        ImmutableSet<String> set1 = new ImmutableSet<>(array);
        ImmutableSet<String> set2 = new ImmutableSet<>(set1);
        assertEquals(3, set2.size());
        assertTrue(set2.contains("un"));
        assertTrue(set2.contains("deux"));
        assertTrue(set2.contains("trois"));
        assertEquals("[un, deux, trois]", set2.toString());
    }

}
