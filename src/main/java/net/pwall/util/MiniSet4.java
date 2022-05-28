/*
 * @(#) MiniSet4.java
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
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of {@link MiniSet} containing four values.
 *
 * @author  Peter Wall
 * @param   <T>     the value type
 */
public class MiniSet4<T> extends MiniSet<T> {

    private final T value0;
    private final T value1;
    private final T value2;
    private final T value3;

    /**
     * Construct a {@code MiniSet4} with the given values.
     *
     * @param   value0      the first value
     * @param   value1      the second value
     * @param   value2      the third value
     * @param   value3      the fourth value
     */
    public MiniSet4(T value0, T value1, T value2, T value3) {
        this.value0 = value0;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    /**
     * Construct a {@code MiniSet4} from another {@link Set} (helps with deserializing).
     *
     * @param   set         the other {@link Set}
     * @throws  IllegalArgumentException if the size of the other set is not 4
     */
    public MiniSet4(Set<T> set) {
        if (set.size() != 4)
            throw new IllegalArgumentException("MiniSet4 size must be 4");
        if (set instanceof MiniSet4) {
            MiniSet4<T> miniSet4 = (MiniSet4<T>)set;
            value0 = miniSet4.value0;
            value1 = miniSet4.value1;
            value2 = miniSet4.value2;
            value3 = miniSet4.value3;
        }
        else {
            Iterator<T> iterator = set.iterator();
            value0 = iterator.next();
            value1 = iterator.next();
            value2 = iterator.next();
            value3 = iterator.next();
        }
    }

    /**
     * Get the number of values (always four).
     *
     * @return      the number of values
     */
    @Override
    public int size() {
        return 4;
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
     * @return          {@code true} if any of the four values is equal to the object
     */
    @Override
    public boolean contains(Object o) {
        return o == null ? value0 == null || value1 == null || value2 == null || value3 == null :
                o.equals(value0) || o.equals(value1) || o.equals(value2) || o.equals(value3);
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
        return new Object[] { value0, value1, value2, value3 };
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
        if (a.length < 4)
            return (TT[])(new Object[] { value0, value1, value2, value3 });
        T[] target = (T[])a;
        target[0] = value0;
        target[1] = value1;
        target[2] = value2;
        target[3] = value3;
        if (target.length > 4)
            target[4] = null;
        return a;
    }

    /**
     * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is also a
     * set containing four values identical to those of this set.
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
        return otherSet.size() == 4 && otherSet.contains(value0) && otherSet.contains(value1) &&
                otherSet.contains(value2) && otherSet.contains(value3);
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
                Objects.hashCode(value3);
    }

    /**
     * Returns a string representation of this set.
     *
     * @return          a string representation of this set
     */
    @Override
    public String toString() {
        return "[" + stringOf(value0) + ", " + stringOf(value1) + ", " + stringOf(value2) + ", " + stringOf(value3) +
                ']';
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
            return index < 4;
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
            T result = index == 0 ? value0 : index == 1 ? value1 : index == 2 ? value2 : value3;
            index++;
            return result;
        }

    }

}
