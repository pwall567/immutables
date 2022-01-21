/*
 * @(#) MiniMap.java
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

import java.util.Map;
import java.util.Objects;

/**
 * An implementation of {@link Map} to provide a simple and fast lookup mechanism for small numbers of keys.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public abstract class MiniMap<K, V> implements Map<K, V> {

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    /**
     * Compares the specified object with this map for equality.  Returns {@code true} if the given object is also a
     * map, the maps contains the same number of entries and for each key in one map, the values returned by both maps
     * are equal (either both are {@code null}, or they compare as equal using {@link Object#equals}).  This ensures
     * that the {@code equals} method works properly across different implementations of the {@code Map} interface.
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
        if (size() != otherMap.size())
            return false;
        for (Entry<K, V> entry : entrySet())
            if (!Objects.equals(otherMap.get(entry.getKey()), entry.getValue()))
                return false;
        return true;
    }

    /**
     * Returns the hash code value for this map.  The hash code of a map is defined to be the sum of the hash codes of
     * each entry in the map's {@code entrySet()} view.  This ensures that {@code m1.equals(m2)} implies that
     * {@code m1.hashCode() == m2.hashCode()} for any two maps {@code m1} and {@code m2}, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return  the hash code value for this map
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (Entry<K, V> entry : entrySet())
            result += entry.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this map.  The string representation consists of the string representations of
     * each of the entries (as key=value), enclosed in braces and separated by a comma and a space.  Keys and values are
     * converted to strings by {@link String#valueOf(Object)}.
     *
     * @return          a string representation of this collection
     */
    @Override
    public String toString() {
        if (size() == 0)
            return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        int i = 0;
        for (Entry<K, V> entry : entrySet()) {
            if (i++ > 0)
                sb.append(',').append(' ');
            sb.append(stringOf(entry.getKey()));
            sb.append('=');
            sb.append(stringOf(entry.getValue()));
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Return a string representation of the supplied key or value, guarding against possible recursion.
     *
     * @param   obj     the object
     * @return          the string representation
     */
    protected String stringOf(Object obj) {
        return obj == this ? "(this Map)" : String.valueOf(obj);
    }

    /**
     * Create an {@link ImmutableMapEntry}.
     *
     * @param   key         the key
     * @param   value       the value
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@link ImmutableMapEntry}
     */
    public static <KK, VV> ImmutableMapEntry<KK, VV> entry(KK key, VV value) {
        return new ImmutableMapEntry<>(key, value);
    }

    /**
     * Create a {@code MiniMap} to map a single key to a nominated value.
     *
     * @param   key         the key
     * @param   value       the value
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> map(KK key, VV value) {
        return new MiniMap1<>(key, value);
    }

    /**
     * Create a {@code MiniMap} to map two keys to two nominated values.
     *
     * @param   key0        the first key
     * @param   value0      the first value
     * @param   key1        the second key
     * @param   value1      the second value
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> map(KK key0, VV value0, KK key1, VV value1) {
        return new MiniMap2<>(key0, value0, key1, value1);
    }

    /**
     * Create a {@code MiniMap} to map three keys to three nominated values.
     *
     * @param   key0        the first key
     * @param   value0      the first value
     * @param   key1        the second key
     * @param   value1      the second value
     * @param   key2        the third key
     * @param   value2      the third value
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> map(KK key0, VV value0, KK key1, VV value1, KK key2, VV value2) {
        return new MiniMap3<>(key0, value0, key1, value1, key2, value2);
    }

    /**
     * Create an empty {@code MiniMap}.
     *
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    @SuppressWarnings("unchecked")
    public static <KK, VV> Map<KK, VV> of() {
        return (MiniMap<KK, VV>)MiniMap0.instance;
    }

    /**
     * Create a {@code MiniMap} with a single {@link Map.Entry}.
     *
     * @param   entry       the entry
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> of(Map.Entry<KK,VV> entry) {
        return new MiniMap1<>(entry.getKey(), entry.getValue());
    }

    /**
     * Create a {@code MiniMap} with two {@link Map.Entry}s.
     *
     * @param   entry0      the first entry
     * @param   entry1      the second entry
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> of(Map.Entry<KK,VV> entry0, Map.Entry<KK,VV> entry1) {
        return new MiniMap2<>(entry0.getKey(), entry0.getValue(), entry1.getKey(), entry1.getValue());
    }

    /**
     * Create a {@code MiniMap} with three {@link Map.Entry}s.
     *
     * @param   entry0      the first entry
     * @param   entry1      the second entry
     * @param   entry2      the third entry
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    public static <KK, VV> Map<KK, VV> of(Map.Entry<KK,VV> entry0, Map.Entry<KK,VV> entry1, Map.Entry<KK,VV> entry2) {
        return new MiniMap3<>(entry0.getKey(), entry0.getValue(), entry1.getKey(), entry1.getValue(), entry2.getKey(),
                entry2.getValue());
    }

    /**
     * Create a {@code MiniMap} with a variable number of {@link Map.Entry}s.
     *
     * @param   entries     the first entries
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code MiniMap}
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <KK, VV> Map<KK, VV> of(Map.Entry<KK,VV> ... entries) {
        int n = entries.length;
        if (n == 0)
            return (Map<KK, VV>)MiniMap0.instance;
        if (n == 1) {
            Map.Entry<KK,VV> entry0 = entries[0];
            return new MiniMap1<>(entry0.getKey(), entry0.getValue());
        }
        if (n == 2) {
            Map.Entry<KK,VV> entry0 = entries[0];
            Map.Entry<KK,VV> entry1 = entries[1];
            return new MiniMap2<>(entry0.getKey(), entry0.getValue(), entry1.getKey(), entry1.getValue());
        }
        if (n == 3) {
            Map.Entry<KK,VV> entry0 = entries[0];
            Map.Entry<KK,VV> entry1 = entries[1];
            Map.Entry<KK,VV> entry2 = entries[2];
            return new MiniMap3<>(entry0.getKey(), entry0.getValue(), entry1.getKey(), entry1.getValue(),
                    entry2.getKey(), entry2.getValue());
        }
        ImmutableMapEntry<KK, VV>[] array = ImmutableMap.createArray(n);
        for (int i = 0; i < n; i++)
            array[i] = ImmutableMap.entry(entries[i].getKey(), entries[i].getValue());
        return new ImmutableMap<>(array);
    }

}
