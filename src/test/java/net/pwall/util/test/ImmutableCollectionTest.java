/*
 * @(#) ImmutableCollectionTest.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2024 Peter Wall
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

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import net.pwall.util.ImmutableCollection;

public class ImmutableCollectionTest {

    @Test
    public void shouldCreateCollection() {
        String[] array = new String[]{"tahi", "rua", "toru", "wha"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        assertEquals(4, collection.size());
        assertTrue(collection.contains("tahi"));
        assertTrue(collection.contains("rua"));
        assertTrue(collection.contains("toru"));
        assertTrue(collection.contains("wha"));
        assertEquals("[tahi, rua, toru, wha]", collection.toString());
    }

    @Test
    public void shouldWorkWithNullEntries() {
        String[] array = new String[]{"tahi", "rua", null, "toru"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        assertEquals(4, collection.size());
        assertTrue(collection.contains("tahi"));
        assertTrue(collection.contains("rua"));
        assertTrue(collection.contains(null));
        assertTrue(collection.contains("toru"));
        assertEquals("[tahi, rua, null, toru]", collection.toString());
    }

    @Test
    public void shouldCreateIterator() {
        String[] array = new String[]{"tahi", "rua", "toru", "wha"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        Iterator<String> iterator = collection.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("tahi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("rua", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("toru", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("wha", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldRejectAttemptToCreateCollectionWithIncorrectLength() {
        String[] array = new String[1];
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableCollection<>(array, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> new ImmutableCollection<>(array, 2));
    }

    @Test
    public void shouldCopyCollection() {
        String[] array = new String[]{"tahi", "rua", "toru", "wha"};
        ImmutableCollection<String> collection1 = new ImmutableCollection<>(array);
        ImmutableCollection<String> collection2 = new ImmutableCollection<>(collection1);
        assertEquals(4, collection2.size());
        assertTrue(collection2.contains("tahi"));
        assertTrue(collection2.contains("rua"));
        assertTrue(collection2.contains("toru"));
        assertTrue(collection2.contains("wha"));
        assertEquals("[tahi, rua, toru, wha]", collection2.toString());
    }

    @Test
    public void shouldThrowErrorOnIndexOutOfBounds() {
        String[] array = new String[] { "one", "two", "three", "four", "five" };
        ImmutableCollection<String> collection = new ImmutableCollection<>(array, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(4));
    }

}
