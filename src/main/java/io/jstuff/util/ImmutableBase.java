/*
 * @(#) ImmutableBase.java
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

/**
 * Base class for immutable collections.  Holds the array and provides functions common to all classes.
 *
 * @author  Peter Wall
 * @param   <A>     the array item type
 */
public abstract class ImmutableBase<A> {

    static final Object[] emptyArray = new Object[0];

    final A[] array;
    final int length;

    /**
     * Construct an {@code ImmutableBase} with the given array and length.
     *
     * @param   array   the array
     * @param   length  the length (the number of array items to be considered part of the collection)
     */
    ImmutableBase(A[] array, int length) {
        this.array = array;
        this.length = length;
    }

    /**
     * Get the number of elements in the collection.
     *
     * @return      the number of elements
     */
    public int size() {
        return length;
    }

    /**
     * Return {@code true} if the collection is empty.
     *
     * @return      {@code true} if the collection is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Check the length of the array when specified explicitly.
     *
     * @param   array       the array
     * @param   length      the specified length
     * @param   <T>         the type of the array
     * @return              the length
     * @throws  IndexOutOfBoundsException if the length is less than 0 or greater than the array length
     */
    static <T> int checkLength(T[] array, int length) {
        if (length < 0 || length > array.length)
            throw new IndexOutOfBoundsException(String.valueOf(length));
        return length;
    }

}
