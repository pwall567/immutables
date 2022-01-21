/*
 * @(#) MiniSet.java
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of {@link Set} to provide a simple "contains" mechanism.
 *
 * @author  Peter Wall
 * @param   <T>     the set element type
 */
public abstract class MiniSet<T> implements Set<T> {

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Test whether the collection contains all of the objects in a supplied {@link Collection}.
     *
     * @param   c       the other {@link Collection}
     * @return          {@code true} if the collection contains all of the objects in the other {@link Collection}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c)
            if (!contains(item))
                return false;
        return true;
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
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
        if (size() != otherSet.size())
            return false;
        for (T item : this)
            if (!otherSet.contains(item))
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
        for (T item : this)
            result += Objects.hashCode(item);
        return result;
    }

    /**
     * Returns a string representation of this collection.  The string representation consists of the string
     * representations of each of the elements, enclosed in square brackets and separated by a comma and a space.
     * Elements are converted to strings by {@link String#valueOf(Object)}.
     *
     * @return          a string representation of this collection
     */
    @Override
    public String toString() {
        if (size() == 0)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        for (T item : this) {
            if (i++ > 0)
                sb.append(',').append(' ');
            sb.append(stringOf(item));
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Return a string representation of the supplied value, guarding against possible recursion.
     *
     * @param   obj     the object
     * @return          the string representation
     */
    protected String stringOf(Object obj) {
        return obj == this ? "(this Collection)" : String.valueOf(obj);
    }

    /**
     * Get an empty {@link MiniSet}.
     *
     * @param   <TT>        the element type
     * @return              an empty {@link MiniSet}
     */
    @SuppressWarnings("unchecked")
    public static <TT> MiniSet<TT> of() {
        return (MiniSet<TT>)MiniSet0.instance;
    }

    /**
     * Get a {@link MiniSet} containing a single value.
     *
     * @param   value       the value
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    public static <TT> MiniSet<TT> of(TT value) {
        return new MiniSet1<>(value);
    }

    /**
     * Get a {@link MiniSet} containing two values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    public static <TT> MiniSet<TT> of(TT value0, TT value1) {
        return new MiniSet2<>(value0, value1);
    }

    /**
     * Get a {@link MiniSet} containing three values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   value2      the third value
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    public static <TT> MiniSet<TT> of(TT value0, TT value1, TT value2) {
        return new MiniSet3<>(value0, value1, value2);
    }

    /**
     * Get a {@link MiniSet} containing four values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   value2      the third value
     * @param   value3      the fourth value
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    public static <TT> MiniSet<TT> of(TT value0, TT value1, TT value2, TT value3) {
        return new MiniSet4<>(value0, value1, value2, value3);
    }

    /**
     * Get a {@link MiniSet} containing five values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   value2      the third value
     * @param   value3      the fourth value
     * @param   value4      the fifth value
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    public static <TT> MiniSet<TT> of(TT value0, TT value1, TT value2, TT value3, TT value4) {
        return new MiniSet5<>(value0, value1, value2, value3, value4);
    }

    /**
     * Get a {@link MiniSet} with a variable number of values.
     *
     * @param   values      the values
     * @param   <TT>        the element type
     * @return              the {@link MiniSet}
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <TT> Set<TT> of(TT ... values) {
        int n = values.length;
        if (n == 0)
            return (Set<TT>)MiniSet0.instance;
        if (n == 1)
            return new MiniSet1<>(values[0]);
        if (n == 2)
            return new MiniSet2<>(values[0], values[1]);
        if (n == 3)
            return new MiniSet3<>(values[0], values[1], values[2]);
        if (n == 4)
            return new MiniSet4<>(values[0], values[1], values[2], values[3]);
        if (n == 5)
            return new MiniSet5<>(values[0], values[1], values[2], values[3], values[4]);
        return new ImmutableSet<>(Arrays.copyOf(values, n));
    }

}
