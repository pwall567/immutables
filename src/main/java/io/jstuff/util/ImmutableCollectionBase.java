/*
 * @(#) ImmutableCollectionBase.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021 Peter Wall
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

import java.util.Collection;

/**
 * Base class for implementations of {@link Collection} and its derived classes.
 *
 * @author  Peter Wall
 * @param   <A>     the array item type
 * @param   <E>     the collection element type
 */
public abstract class ImmutableCollectionBase<A, E> extends ImmutableBase<A> implements Collection<E> {

    /**
     * Construct an {@code ImmutableCollectionBase} with the supplied array and length.
     *
     * @param   array   the array
     * @param   length  the length (the number of array items to be considered part of the collection)
     */
    ImmutableCollectionBase(A[] array, int length) {
        super(array, length);
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
    public boolean add(E e) {
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
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
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
     * Returns a string representation of this collection.  The string representation consists of the string
     * representations of each of the elements, enclosed in square brackets and separated by a comma and a space.
     * Elements are converted to strings by {@link String#valueOf(Object)}.
     *
     * @return          a string representation of this collection
     */
    @Override
    public String toString() {
        if (length == 0)
            return "[]";
        StringBuilder sb = new StringBuilder(length * 16);
        sb.append('[');
        int i = 0;
        while (true) {
            A item = array[i];
            sb.append(item == this ? "(this Collection)" : item);
            if (++i >= length)
                break;
            sb.append(',').append(' ');
        }
        sb.append(']');
        return sb.toString();
    }

}
