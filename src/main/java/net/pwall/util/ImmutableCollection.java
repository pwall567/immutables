/*
 * @(#) ImmutableCollection.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2022, 2023, 2024 Peter Wall
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
import java.util.Iterator;

/**
 * Immutable implementation of {@link Collection}.
 *
 * @author  Peter Wall
 * @param   <T>     the collection element type
 */
public class ImmutableCollection<T> extends ImmutableCollectionBase<T, T> implements Collection<T> {

    /**
     * Construct an {@code ImmutableCollection} with the given array and length.
     * <br>
     * **IMPORTANT &ndash; the collection is immutable only if the array is not subsequently modified.**
     *
     * @param   array   the array
     * @param   length  the length (the number of array items to be considered part of the collection)
     * @throws  IndexOutOfBoundsException if the length is less than 0 or greater than the array length
     */
    public ImmutableCollection(T[] array, int length) {
        super(array, checkLength(array, length));
    }

    /**
     * Construct an {@code ImmutableCollection} with the given array (using the entire array).
     * <br>
     * **IMPORTANT &ndash; the collection is immutable only if the array is not subsequently modified.**
     *
     * @param   array   the array
     */
    public ImmutableCollection(T[] array) {
        super(array, array.length);
    }

    /**
     * Construct an {@code ImmutableCollection} from another {@link Collection} (helps with deserializing).
     *
     * @param   collection  the other {@link Collection}
     */
    @SuppressWarnings("unchecked")
    public ImmutableCollection(Collection<T> collection) {
        super((T[])collection.toArray(new Object[0]), collection.size());
    }

    /**
     * Internal constructor to prevent repeating length check.
     *
     * @param  length   the length
     * @param   array   the array
     */
    protected ImmutableCollection(int length, T[] array) {
        super(array, length);
    }

    /**
     * Test whether the collection contains an object equal to the specified value (which may be {@code null}).
     *
     * @param   o       the object
     * @return          {@code true} if the collection contains the object
     */
    @Override
    public boolean contains(Object o) {
        return contains(array, length, o);
    }

    /**
     * Return an {@link Iterator} over the collection.
     *
     * @return          the {@link Iterator}
     */
    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator<>(array, length, 0);
    }

    /**
     * Create a copy of the underlying array.
     * <br>
     * <b>NOTE:</b> as an optimisation, when the size of the collection is zero, a constant <code>emptyArray</code> (of
     * type <code>Object[]</code>) will be returned.  This may affect identity comparisons using the result array, or
     * any attempt to discover dynamically the class of the array items.
     *
     * @return          a copy of the active portion of the array
     */
    @Override
    public Object[] toArray() {
        return length == 0 ? emptyArray : Arrays.copyOf(array, length);
    }

    /**
     * Return a copy of the underlying array, either in the supplied array (if it fits) or in a new array of the
     * specified type.
     * <br>
     * <b>NOTE:</b> as an optimisation, when the size of the collection is zero, a constant <code>emptyArray</code> (of
     * type <code>Object[]</code>) will be returned.  This may affect identity comparisons using the result array, or
     * any attempt to discover dynamically the class of the array items.
     *
     * @param   a       the destination array
     * @param   <TT>    the type of the destination array items
     * @return          a copy of the active portion of the collection array, in a new array if necessary
     * @throws  ArrayStoreException if the values in the collection can't be stored in the destination array
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TT> TT[] toArray(TT[] a) {
        if (length == 0)
            return (TT[])emptyArray;
        if (a.length < length)
            return (TT[])Arrays.copyOf(array, length, a.getClass());
        T[] target = (T[])a;
        System.arraycopy(array, 0, target, 0, length);
        if (target.length > length)
            target[length] = null;
        return a;
    }

    /**
     * Get the element at the specified index.  This is not part of the standard {@link Collection} interface, but it
     * allows iteration over the members of a collection without needing to instantiate an {@link Iterator}.
     *
     * @param   index       the index
     * @return              the element at that index
     * @throws  IndexOutOfBoundsException   if the index is less than 0 or beyond the end of the collection
     */
    public T get(int index) {
        return get(array, length, index);
    }

    /**
     * Get the element of an array at the specified index, where the array may be longer than the number of elements
     * allowed to be accessed.
     *
     * @param   array   the array
     * @param   length  the number of elements in the array allowed to be accessed
     * @param   index   the index
     * @param   <TT>    the type of the array items
     * @return          the element at that index
     * @throws  IndexOutOfBoundsException   if the index is less than 0 or beyond the allowed number of elements
     */
    public static <TT> TT get(TT[] array, int length, int index) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        return array[index];
    }

    /**
     * Test whether a given array contains an object equal to the specified value (which may be {@code null}).
     *
     * @param   array   the array
     * @param   length  the number of elements in the array to be examined
     * @param   o       the object
     * @param   <TT>    the type of the array items
     * @return          {@code true} if the collection contains the object
     */
    public static <TT> boolean contains(TT[] array, int length, Object o) {
        if (o == null) {
            for (int i = 0; i < length; i++)
                if (array[i] == null)
                    return true;
        }
        else {
            for (int i = 0; i < length; i++)
                if (o.equals(array[i]))
                    return true;
        }
        return false;
    }

}
