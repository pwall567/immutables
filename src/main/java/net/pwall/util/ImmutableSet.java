/*
 * @(#) ImmutableSet.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2023 Peter Wall
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

import java.util.Objects;
import java.util.Set;

/**
 * Immutable implementation of {@link Set}.
 *
 * @author  Peter Wall
 * @param   <T>     the set element type
 */
public class ImmutableSet<T> extends ImmutableCollection<T> implements Set<T> {

    /**
     * Construct an {@code ImmutableSet} with the given array and length.
     * <br>
     * **IMPORTANT &ndash; the set is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     * @param   length      the length (the number of array items to be considered part of the set)
     * @throws  IndexOutOfBoundsException if the length is less than 0 or greater than the array length
     */
    public ImmutableSet(T[] array, int length) {
        super(checkLength(array, length), array);
    }

    /**
     * Construct an {@code ImmutableSet} with the given array (using the entire array).
     * <br>
     * **IMPORTANT &ndash; the set is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     */
    public ImmutableSet(T[] array) {
        super(array.length, array);
    }

    /**
     * Construct an {@code ImmutableSet} from another {@link Set} (helps with deserializing).
     *
     * @param   set         the other {@link Set}
     */
    @SuppressWarnings("unchecked")
    public ImmutableSet(Set<T> set) {
        super(set.size(), (T[])set.toArray(new Object[0]));
    }

    /**
     * Internal constructor to prevent repeating length check.
     *
     * @param  length   the length
     * @param   array   the array
     */
    private ImmutableSet(int length, T[] array) {
        super(length, array);
    }

    /**
     * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is also a
     * set, the two sets have the same size, and every member of the specified set is contained in this set (or
     * equivalently, every member of this set is contained in the specified set).  This definition ensures that the
     * equals method works properly across different implementations of the set interface.
     *
     * @param   other   object to be compared for equality with this set
     * @return  {@code true} if the specified object is equal to this set
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Set))
            return false;
        Set<?> otherSet = (Set<?>)other;
        if (length != otherSet.size())
            return false;
        for (int i = 0; i < length; i++)
            if (!otherSet.contains(array[i]))
                return false;
        return true;
    }

    /**
     * Returns the hash code value for this set.  The hash code of a set is defined to be the sum of the hash codes of
     * the elements in the set, where the hash code of a {@code null} element is defined to be zero.  This ensures that
     * {@code s1.equals(s2)} implies that {@code s1.hashCode() == s2.hashCode()} for any two sets {@code s1} and
     * {@code s2}, as required by the general contract of {@link Object#hashCode}.
     *
     * @return  the hash code value for this set
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < length; i++)
            result += Objects.hashCode(array[i]);
        return result;
    }

    /**
     * Get an empty {@code ImmutableSet}.  This avoids a memory allocation if the set is empty.
     *
     * @param   <TT>        the element type
     * @return              an empty set
     */
    @SuppressWarnings("unchecked")
    public static <TT> ImmutableSet<TT> emptySet() {
        return new ImmutableSet<>(0, (TT[])emptyArray);
    }

    /**
     * Get an {@code ImmutableSet} using the supplied array.  If the array length is zero, the empty set is returned.
     *
     * @param   array       the array
     * @param   <TT>        the element type
     * @return              the set
     */
    public static <TT> ImmutableSet<TT> setOf(TT[] array) {
        int size = array.length;
        return size == 0 ? emptySet() : new ImmutableSet<>(size, array);
    }

    /**
     * Get an {@code ImmutableSet} using the supplied array.  If the array length is zero, the empty set is returned.
     *
     * @param   array       the array
     * @param   length      the length (the number of array items to be considered part of the set)
     * @param   <TT>        the element type
     * @return              the set
     */
    public static <TT> ImmutableSet<TT> setOf(TT[] array, int length) {
        return length == 0 ? emptySet() : new ImmutableSet<>(array, length);
    }

}
