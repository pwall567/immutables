/*
 * @(#) MiniMap1.java
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
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of {@link MiniMap} containing a single entry.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public class MiniMap1<K, V> extends MiniMap<K, V> {

    private final K key;
    private final V value;

    /**
     * Construct a {@code MiniMap1} with the given key and value.
     *
     * @param   key         the key
     * @param   value       the value
     */
    public MiniMap1(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Construct a {@code MiniMap1} from another {@link Map} (helps with deserializing).
     *
     * @param   map         the other {@link Map}
     * @throws  IllegalArgumentException if the size of the other map is not 1
     */
    public MiniMap1(Map<K, V> map) {
        if (map.size() != 1)
            throw new IllegalArgumentException("MiniMap1 size must be 1");
        if (map instanceof MiniMap1) {
            MiniMap1<K, V> miniMap1 = (MiniMap1<K, V>)map;
            key = miniMap1.key;
            value = miniMap1.value;
        }
        else {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            Map.Entry<K, V> entry = iterator.next();
            key = entry.getKey();
            value = entry.getValue();
        }
    }

    /**
     * Get the number of entries (always one).
     *
     * @return      the number of entries
     */
    @Override
    public int size() {
        return 1;
    }

    /**
     * Return {@code false} to indicate that the map is not empty.
     *
     * @return      {@code false}
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Test whether the map contains a key equal to the specified key (which may be {@code null}).
     *
     * @param   key         the key
     * @return              {@code true} if the map contains the key
     */
    @Override
    public boolean containsKey(Object key) {
        return Objects.equals(this.key, key);
    }

    /**
     * Test whether the map contains a value equal to the specified value (which may be {@code null}).
     *
     * @param   value       the value
     * @return              {@code true} if the map contains the value
     */
    @Override
    public boolean containsValue(Object value) {
        return Objects.equals(this.value, value);
    }

    /**
     * Get the value corresponding to the supplied key.  If the key is not found, {@code null} is returned.
     *
     * @param   key         the key
     * @return              the value
     */
    @Override
    public V get(Object key) {
        return Objects.equals(this.key, key) ? value : null;
    }

    /**
     * Get a {@link Set} of the keys in use in this map.  Returns a {@link Set} containing the single key.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<K> keySet() {
        return new MiniSet1<>(key);
    }

    /**
     * Get a {@link Collection} of the values in this map.  Returns a {@link Collection} containing the single value.
     *
     * @return              the {@link Collection}
     */
    @Override
    public Collection<V> values() {
        return new MiniSet1<>(value);
    }

    /**
     * Get a {@link Set} of the entries in this map.  Returns a {@link Set} containing the single entry.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new MiniSet1<>(entry(key, value));
    }

    /**
     * Compares the specified object with this map for equality.  Returns {@code true} if the given object is also a
     * map containing a single entry identical to that of this map.
     *
     * @param   other           object to be compared for equality with this map
     * @return                  {@code true} if the specified object is equal to this map
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Map))
            return false;
        Map<?, ?> otherMap = (Map<?, ?>)other;
        return otherMap.size() == 1 && otherMap.containsKey(key) && Objects.equals(value, otherMap.get(key));
    }

    /**
     * Returns the hash code value for this map.  The hash code of a map with a single entry is defined to be the hash
     * code of the entry.
     *
     * @return  the hash code value for this map
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    /**
     * Returns a string representation of this map.
     *
     * @return          a string representation of this map
     */
    @Override
    public String toString() {
        return "{" + stringOf(key) + '=' + stringOf(value) + '}';
    }

}
