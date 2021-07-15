/*
 * @(#) ImmutableIterator.java
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

package net.pwall.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of {@link Iterator} to provide functionality for the immutable classes.
 *
 * @author  Peter Wall
 * @param   <T>     the collection element type
 */
public class ImmutableIterator<T> extends ImmutableIteratorBase<T> implements Iterator<T> {

    /**
     * Construct an {@code ImmutableIterator} with the given array and length, and with the given starting index.
     *
     * @param   array   the array
     * @param   length  the length (the number of array items to be considered part of the collection)
     * @param   index   the starting index
     */
    public ImmutableIterator(T[] array, int length, int index) {
        super(array, length, index);
    }

    /**
     * Get the next element referenced by this {@code ImmutableIterator}.
     *
     * @return          the next element
     * @throws  NoSuchElementException  if there is no "next" element
     */
    @Override
    public T next() {
        return checkNext();
    }

}
