/*
 * @(#) ImmutableList.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021, 2022, 2023 Peter Wall
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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;

/**
 * Immutable implementation of {@link List}.
 *
 * @author  Peter Wall
 * @param   <T>     the list element type
 */
public class ImmutableList<T> extends ImmutableCollection<T> implements List<T>, RandomAccess {

    /**
     * Construct an {@code ImmutableList} with the given array and length.
     * <br>
     * **IMPORTANT &ndash; the list is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     * @param   length      the length (the number of array items to be considered part of the list)
     * @throws  IndexOutOfBoundsException if the length is less than 0 or greater than the array length
     */
    public ImmutableList(T[] array, int length) {
        super(checkLength(array, length), array);
    }

    /**
     * Construct an {@code ImmutableList} with the given array (using the entire array).
     * <br>
     * **IMPORTANT &ndash; the list is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     */
    public ImmutableList(T[] array) {
        super(array.length, array);
    }

    /**
     * Construct an {@code ImmutableList} from another {@link List} (helps with deserializing).
     *
     * @param   list        the other {@link List}
     */
    @SuppressWarnings("unchecked")
    public ImmutableList(List<T> list) {
        super(list.size(), (T[])list.toArray(new Object[0]));
    }

    /**
     * Internal constructor to prevent repeating length check.
     *
     * @param  length   the length
     * @param   array   the array
     */
    private ImmutableList(int length, T[] array) {
        super(length, array);
    }

    /**
     * Find the index in the list of the first item equal to the given object (either both {@code null}, or equal
     * according to {@link Object#equals}).
     *
     * @param   o       the object to be located
     * @return          the index, or -1 if not found
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < length; i++)
                if (array[i] == null)
                    return i;
        }
        else {
            for (int i = 0; i < length; i++)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Find the index in the list of the last item equal to the given object (either both {@code null}, or equal
     * according to {@link Object#equals}).
     *
     * @param   o       the object to be located
     * @return          the index, or -1 if not found
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = length - 1; i >= 0; i--)
                if (array[i] == null)
                    return i;
        }
        else {
            for (int i = length - 1; i >= 0; i--)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Get a {@link ListIterator} over this list.
     *
     * @return          the {@link ListIterator}
     */
    @Override
    public ListIterator<T> listIterator() {
        return new ImmutableListIterator<>(array, length, 0);
    }

    /**
     * Get a {@link ListIterator} over this list, initialised to the given index.
     *
     * @param   index   the starting index
     * @return          the {@link ListIterator}
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return new ImmutableListIterator<>(array, length, index);
    }

    /**
     * Get a sub-list of this list.
     *
     * @param   fromIndex   the starting index of the sub-list
     * @param   toIndex     the ending index of the sub-list
     * @return              the sub-list
     * @throws  IndexOutOfBoundsException   if the index range is not valid
     */
    @Override
    public ImmutableList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex == 0) {
            if (toIndex == length)
                return this;
            if (toIndex >= 0 && toIndex < length)
                return new ImmutableList<>(toIndex, array);
        }
        if (fromIndex < 0 || toIndex > length || toIndex < fromIndex)
            throw new IndexOutOfBoundsException();
        int newLength = toIndex - fromIndex;
        if (newLength == 0)
            return emptyList();
        @SuppressWarnings("unchecked")
        T[] newArray = (T[])Array.newInstance(array.getClass().getComponentType(), newLength);
        System.arraycopy(array, fromIndex, newArray, 0, newLength);
        return new ImmutableList<>(newArray);
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Compares the specified object with this list for equality.  Returns {@code true} if and only if the specified
     * object is also a list, both lists have the same size, and all corresponding pairs of elements in the two lists
     * are equal (that is, both are null or they compare as equal using {@link Object#equals}).  This definition ensures
     * that the {@link Object#equals} method works properly across different implementations of the {@link List}
     * interface.
     *
     * @param   other       the object to be compared for equality with this list
     * @return              {@code true} if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof List))
            return false;
        List<?> otherList = (List<?>)other;
        if (length != otherList.size())
            return false;
        if (otherList instanceof RandomAccess) {
            for (int i = 0; i < length; i++)
                if (!Objects.equals(array[i], otherList.get(i)))
                    return false;
        }
        else {
            Iterator<?> iterator = otherList.iterator();
            for (int i = 0; i < length; i++)
                if (!Objects.equals(array[i], iterator.next()))
                    return false;
        }
        return true;
    }

    /**
     * Returns the hash code value for this list.
     *
     * <p>This implementation uses exactly the code that is used to define the list hash function in the documentation
     * for the {@link List#hashCode} method.</p>
     *
     * @return  the hash code value for this list
     */
    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < length; i++)
            result = 31 * result + Objects.hashCode(array[i]);
        return result;
    }

    /**
     * Get an empty {@code ImmutableList}.  This avoids a memory allocation if the list is empty.
     *
     * @param   <TT>        the element type
     * @return              an empty list
     */
    @SuppressWarnings("unchecked")
    public static <TT> ImmutableList<TT> emptyList() {
        return new ImmutableList<>((TT[])emptyArray);
    }

    /**
     * Get an {@code ImmutableList} using the supplied array.  If the array length is zero, the empty list is returned.
     * <br>
     * **IMPORTANT &ndash; the list is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     * @param   <TT>        the element type
     * @return              the list
     */
    public static <TT> ImmutableList<TT> listOf(TT[] array) {
        return array.length == 0 ? emptyList() : new ImmutableList<>(array);
    }

    /**
     * Get an {@code ImmutableList} using the supplied array.  If the array length is zero, the empty list is returned.
     * <br>
     * **IMPORTANT &ndash; the list is immutable only if the array is not subsequently modified.**
     *
     * @param   array       the array
     * @param   length      the length (the number of array items to be considered part of the list)
     * @param   <TT>        the element type
     * @return              the list
     */
    public static <TT> ImmutableList<TT> listOf(TT[] array, int length) {
        return length == 0 ? emptyList() : new ImmutableList<>(array, length);
    }

}
