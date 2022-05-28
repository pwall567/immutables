/*
 * @(#) MiniSet0.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An empty implementation of {@link MiniSet}.
 *
 * @author  Peter Wall
 * @param   <T>     the value type
 */
public class MiniSet0<T> extends MiniSet<T> {

    /** A reusable instance. */
    public static final MiniSet0<?> instance = new MiniSet0<>();

    /**
     * Construct a {@code MiniSet0}.
     */
    public MiniSet0() {
    }

    /**
     * Construct a {@code MiniSet0} from another {@link Set} (helps with deserializing).
     *
     * @param   set         the other {@link Set}
     * @throws  IllegalArgumentException if the size of the other set is not 0
     */
    public MiniSet0(Set<T> set) {
        if (set.size() != 0)
            throw new IllegalArgumentException("MiniSet0 size must be 0");
    }

    /**
     * Get the number of values (always zero).
     *
     * @return      the number of values
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Return {@code true} to indicate that the set is empty.
     *
     * @return      {@code true}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * Test whether the collection contains an object equal to the specified value (always {@code false}).
     *
     * @param   o       the object
     * @return          {@code true} if the collection contains the object
     */
    @Override
    public boolean contains(Object o) {
        return false;
    }

    /**
     * Return an {@link Iterator} over the set.
     *
     * @return          the {@link Iterator}
     */
    @Override
    public Iterator<T> iterator() {
        return new Miniterator();
    }

    /**
     * Copy the set to an array (always an empty array).
     *
     * @return          an empty array
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Copy the set to an existing array (with {@code null} end marker if the destination array is not of length zero).
     *
     * @param   a       the destination array
     * @param   <TT>    the type of the destination array items
     * @return          the destination array
     */
    @Override
    public <TT> TT[] toArray(TT[] a) {
        if (a.length > 0)
            a[0] = null;
        return a;
    }

    /**
     * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is also a
     * set, and it is also empty.
     *
     * @param   other   object to be compared for equality with this set
     * @return  {@code true} if the specified object is equal to this set
     */
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Set && ((Set<?>)other).isEmpty();
    }

    /**
     * Returns the hash code value for this set.    The hash code of an empty set is always zero.
     *
     * @return  the hash code value for this set
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns a string representation of this set.
     *
     * @return          a string representation of this set
     */
    @Override
    public String toString() {
        return "[]";
    }

    /**
     * An {@link Iterator} over the empty set.
     */
    public class Miniterator implements Iterator<T> {

        /**
         * Test whether the iterator has any more elements (always {@code false}).
         *
         * @return      {@code false}
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Get the next element referenced by this iterator (always throws exception).
         *
         * @throws  NoSuchElementException  in all cases
         */
        @Override
        public T next() {
            throw new NoSuchElementException();
        }

    }

}
