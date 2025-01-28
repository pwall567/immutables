/*
 * @(#) MiniSet5.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of {@link MiniSet} containing four values.
 *
 * @author  Peter Wall
 * @param   <T>     the value type
 */
public class MiniSet5<T> extends MiniSet<T> {

    private final T value0;
    private final T value1;
    private final T value2;
    private final T value3;
    private final T value4;

    /**
     * Construct a {@code MiniSet5} with the given values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   value2      the third value
     * @param   value3      the fourth value
     * @param   value4      the fifth value
     */
    public MiniSet5(T value0, T value1, T value2, T value3, T value4) {
        this.value0 = value0;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    /**
     * Construct a {@code MiniSet5} from another {@link Set} (helps with deserializing).
     *
     * @param   set         the other {@link Set}
     * @throws  IllegalArgumentException if the size of the other set is not 5
     */
    public MiniSet5(Set<T> set) {
        if (set.size() != 5)
            throw new IllegalArgumentException("MiniSet5 size must be 5");
        if (set instanceof MiniSet5) {
            MiniSet5<T> miniSet5 = (MiniSet5<T>)set;
            value0 = miniSet5.value0;
            value1 = miniSet5.value1;
            value2 = miniSet5.value2;
            value3 = miniSet5.value3;
            value4 = miniSet5.value4;
        }
        else {
            Iterator<T> iterator = set.iterator();
            value0 = iterator.next();
            value1 = iterator.next();
            value2 = iterator.next();
            value3 = iterator.next();
            value4 = iterator.next();
        }
    }

    /**
     * Get the number of values (always five).
     *
     * @return      the number of values
     */
    @Override
    public int size() {
        return 5;
    }

    /**
     * Return {@code false} to indicate that the set is not empty.
     *
     * @return      {@code false}
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Test whether the collection contains an object equal to the specified value.
     *
     * @param   o       the object
     * @return          {@code true} if any of the five values is equal to the object
     */
    @Override
    public boolean contains(Object o) {
        return o == null ? value0 == null || value1 == null || value2 == null || value3 == null || value4 == null :
            o.equals(value0) || o.equals(value1) || o.equals(value2) || o.equals(value3) || o.equals(value4);
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
     * Copy the set to an array.
     *
     * @return          an array containing the values
     */
    @Override
    public Object[] toArray() {
        return new Object[] { value0, value1, value2, value3, value4 };
    }

    /**
     * Copy the set to an array, either the supplied array (if it fits) or a new array of the specified type.
     *
     * @param   a       the destination array
     * @param   <TT>    the type of the destination array items
     * @return          a copy of the set, in a new array if necessary
     * @throws  ArrayStoreException if the values in the collection can't be stored in the destination array
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TT> TT[] toArray(TT[] a) {
        if (a.length < 5)
            return (TT[])(new Object[] { value0, value1, value2, value3, value4 });
        T[] target = (T[])a;
        target[0] = value0;
        target[1] = value1;
        target[2] = value2;
        target[3] = value3;
        target[4] = value4;
        if (target.length > 5)
            target[5] = null;
        return a;
    }

    /**
     * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is also a
     * set containing five values identical to those of this set.
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
        return otherSet.size() == 5 && otherSet.contains(value0) && otherSet.contains(value1) &&
                otherSet.contains(value2) && otherSet.contains(value3) && otherSet.contains(value4);
    }

    /**
     * Returns the hash code value for this set.  The hash code of a set is defined to be the sum of the hash codes of
     * the elements in the set.
     *
     * @return  the hash code value for this set
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value0) + Objects.hashCode(value1) + Objects.hashCode(value2) +
                Objects.hashCode(value3) + Objects.hashCode(value4);
    }

    /**
     * Returns a string representation of this set.
     *
     * @return          a string representation of this set
     */
    @Override
    public String toString() {
        return "[" + stringOf(value0) + ", " + stringOf(value1) + ", " + stringOf(value2) + ", " + stringOf(value3) +
                ", " + stringOf(value4) + ']';
    }

    /**
     * An {@link Iterator} over a set with two values.
     */
    public class Miniterator implements Iterator<T> {

        private int index = 0;

        /**
         * Test whether the iterator has any more elements.
         *
         * @return          {@code true} if there is at least one element available
         */
        @Override
        public boolean hasNext() {
            return index < 5;
        }

        /**
         * Get the next element referenced by this iterator.
         *
         * @return          the next element
         * @throws  NoSuchElementException  if there is no "next" element
         */
        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T result = index == 0 ? value0 : index == 1 ? value1 : index == 2 ? value2 : index == 3 ? value3 : value4;
            index++;
            return result;
        }

    }

}
