/*
 * @(#) ImmutableMapEntry.java
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

package io.jstuff.util;

import java.util.Map;
import java.util.Objects;

/**
 * An immutable {@link Map.Entry}.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public class ImmutableMapEntry<K, V> implements Map.Entry<K, V> {

    private final K key;
    private final V value;

    /**
     * Construct an {@code ImmutableMapEntry} with the given key and value.
     *
     * @param   key         the key
     * @param   value       the value
     */
    public ImmutableMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key.
     *
     * @return      the key
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Get the value.
     *
     * @return      the value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Compare with another object for equality.  Any other {@link Map.Entry} with the same key and value is
     * considered equal (where the key and value equality tests are done by {@link Objects#equals}.
     *
     * @param   other       the other object
     * @return              {@code true} if the objects are equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Map.Entry))
            return false;
        Map.Entry<?, ?> otherMapEntry = (Map.Entry<?, ?>)other;
        return Objects.equals(key, otherMapEntry.getKey()) && Objects.equals(value, otherMapEntry.getValue());
    }

    /**
     * This is coded to match exactly the hash calculation used by {@link java.util.HashMap}.
     *
     * @return  the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    /**
     * Create a string representation of this {@code ImmutableMapEntry}, in the form "key=value".
     *
     * @return      the string representation
     */
    @Override
    public String toString() {
        return String.valueOf(key) + '=' + value;
    }

}
