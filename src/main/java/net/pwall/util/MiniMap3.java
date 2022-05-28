/*
 * @(#) MiniMap3.java
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
 * An implementation of {@link MiniMap} containing three entries.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public class MiniMap3<K, V> extends MiniMap<K, V> {

    private final K key0;
    private final V value0;
    private final K key1;
    private final V value1;
    private final K key2;
    private final V value2;

    /**
     * Construct a {@code MiniMap2} with the given keys and values.
     *
     * @param   key0        the first key
     * @param   value0      the first value
     * @param   key1        the second key
     * @param   value1      the second value
     * @param   key2        the third key
     * @param   value2      the third value
     */
    public MiniMap3(K key0, V value0, K key1, V value1, K key2, V value2) {
        this.key0 = key0;
        this.value0 = value0;
        this.key1 = key1;
        this.value1 = value1;
        this.key2 = key2;
        this.value2 = value2;
    }

    /**
     * Construct a {@code MiniMap3} from another {@link Map} (helps with deserializing).
     *
     * @param   map         the other {@link Map}
     * @throws  IllegalArgumentException if the size of the other map is not 3
     */
    public MiniMap3(Map<K, V> map) {
        if (map.size() != 3)
            throw new IllegalArgumentException("MiniMap3 size must be 3");
        if (map instanceof MiniMap3) {
            MiniMap3<K, V> miniMap3 = (MiniMap3<K, V>)map;
            key0 = miniMap3.key0;
            value0 = miniMap3.value0;
            key1 = miniMap3.key1;
            value1 = miniMap3.value1;
            key2 = miniMap3.key2;
            value2 = miniMap3.value2;
        }
        else {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            Map.Entry<K, V> entry = iterator.next();
            key0 = entry.getKey();
            value0 = entry.getValue();
            entry = iterator.next();
            key1 = entry.getKey();
            value1 = entry.getValue();
            entry = iterator.next();
            key2 = entry.getKey();
            value2 = entry.getValue();
        }
    }

    /**
     * Get the number of entries (always three).
     *
     * @return      the number of entries
     */
    @Override
    public int size() {
        return 3;
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
        return key == null ? key0 == null || key1 == null || key2 == null :
                key.equals(key0) || key.equals(key1) || key.equals(key2);
    }

    /**
     * Test whether the map contains a value equal to the specified value (which may be {@code null}).
     *
     * @param   value       the value
     * @return              {@code true} if the map contains the value
     */
    @Override
    public boolean containsValue(Object value) {
        return value == null ? value0 == null || value1 == null || value2 == null :
                value.equals(value0) || value.equals(value1) || value.equals(value2);
    }

    /**
     * Get the value corresponding to the supplied key.  If the key is not found, {@code null} is returned.
     *
     * @param   key         the key
     * @return              the value
     */
    @Override
    public V get(Object key) {
        return key == null ? (key0 == null ? value0 : key1 == null ? value1 : key2 == null ? value2 : null) :
                (key.equals(key0) ? value0 : key.equals(key1) ? value1 : key.equals(key2) ? value2 : null);
    }

    /**
     * Get a {@link Set} of the keys in use in this map.  Returns a {@link Set} containing the three keys.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<K> keySet() {
        return new MiniSet3<>(key0, key1, key2);
    }

    /**
     * Get a {@link Collection} of the values in this map.  Returns a {@link Collection} containing the three values.
     *
     * @return              the {@link Collection}
     */
    @Override
    public Collection<V> values() {
        return new MiniSet3<>(value0, value1, value2);
    }

    /**
     * Get a {@link Set} of the entries in this map.  Returns a {@link Set} containing the three entries.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new MiniSet3<>(entry(key0, value0), entry(key1, value1), entry(key2, value2));
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
        return otherMap.size() == 3 && otherMap.containsKey(key0) && Objects.equals(value0, otherMap.get(key0)) &&
                otherMap.containsKey(key1) && Objects.equals(value1, otherMap.get(key1)) &&
                otherMap.containsKey(key2) && Objects.equals(value2, otherMap.get(key2));
    }

    /**
     * Returns the hash code value for this map.  The hash code of a map is defined to be the sum of the hash codes of
     * each entry in the map's {@code entrySet()} view.
     *
     * @return  the hash code value for this map
     */
    @Override
    public int hashCode() {
        return (Objects.hashCode(key0) ^ Objects.hashCode(value0)) +
                (Objects.hashCode(key1) ^ Objects.hashCode(value1)) +
                (Objects.hashCode(key2) ^ Objects.hashCode(value2));
    }

    /**
     * Returns a string representation of this map.
     *
     * @return          a string representation of this map
     */
    @Override
    public String toString() {
        return "{" + stringOf(key0) + '=' + stringOf(value0) + ", " + stringOf(key1) + '=' + stringOf(value1) + ", " +
                stringOf(key2) + '=' + stringOf(value2) + '}';
    }

}
