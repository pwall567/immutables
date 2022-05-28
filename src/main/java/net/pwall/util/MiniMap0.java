/*
 * @(#) MiniMap0.java
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

package net.pwall.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An empty implementation of {@link MiniMap}.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public class MiniMap0<K, V> extends MiniMap<K, V> {

    /** A reusable instance. */
    public static final MiniMap0<?, ?> instance = new MiniMap0<>();

    /**
     * Construct a {@code MiniMap0}.
     */
    public MiniMap0() {
    }

    /**
     * Construct a {@code MiniMap0} from another {@link Map} (helps with deserializing).
     *
     * @param   map         the other {@link Map}
     * @throws  IllegalArgumentException if the size of the other map is not 0
     */
    public MiniMap0(Map<K, V> map) {
        if (map.size() != 0)
            throw new IllegalArgumentException("MiniMap0 size must be 0");
    }

    /**
     * Get the number of entries (always zero).
     *
     * @return      the number of entries
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Return {@code true} to indicate that the map is empty.
     *
     * @return      {@code true}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * Test whether the map contains a key equal to the specified key (always {@code false}).
     *
     * @param   key         the key
     * @return              {@code false}
     */
    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    /**
     * Test whether the map contains a value equal to the specified value (always {@code false}).
     *
     * @param   value       the value
     * @return              {@code false}
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * Get the value corresponding to the supplied key.  Always returns {@code null}.
     *
     * @param   key         the key
     * @return              {@code null}
     */
    @Override
    public V get(Object key) {
        return null;
    }

    /**
     * Get a {@link Set} of the keys in use in this map.  Always returns an empty {@link Set}.
     *
     * @return              an empty {@link Set}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keySet() {
        return (Set<K>)MiniSet0.instance;
    }

    /**
     * Get a {@link Collection} of the values in this map.  Always returns an empty {@link Collection}.
     *
     * @return              an empty {@link Collection}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        return (Collection<V>)MiniSet0.instance;
    }

    /**
     * Get a {@link Set} of the entries in this map.  Always returns an empty {@link Set}.
     *
     * @return              an empty {@link Set}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>)MiniSet0.instance;
    }

    /**
     * Compares the specified object with this map for equality.  Returns {@code true} if the given object is also a
     * map, and it is also empty.
     *
     * @param   other           object to be compared for equality with this map
     * @return                  {@code true} if the specified object is equal to this map
     */
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Map && ((Map<?, ?>)other).isEmpty();
    }

    /**
     * Returns the hash code value for this map.  The hash code of an empty map is always zero.
     *
     * @return  the hash code value for this map
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns a string representation of this map.
     *
     * @return          a string representation of this map
     */
    @Override
    public String toString() {
        return "{}";
    }

}
