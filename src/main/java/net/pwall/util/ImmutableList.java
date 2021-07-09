package net.pwall.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;

public class ImmutableList<T> extends ImmutableCollection<T> implements List<T>, RandomAccess {

    public ImmutableList(T[] array, int length) {
        super(array, length);
    }

    public ImmutableList(T[] array) {
        this(array, array.length);
    }

    @Override
    public T get(int index) {
        // no need to check index - array indexing will check anyway
        return array[index];
    }

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

    @Override
    public ListIterator<T> listIterator() {
        return new ImmutableListIterator<>(array, length, 0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ImmutableListIterator<>(array, length, index);
    }

    @Override
    public ImmutableList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == length)
            return this;
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
     * Compares the specified object with this list for equality.  Returns {@code true} if and only if the specified
     * object is also a list, both lists have the same size, and all corresponding pairs of elements in the two lists
     * are <i>equal</i>.  (Two elements {@code e1} and {@code e2} are <i>equal</i> if
     * {@code (e1 == null ? e2 == null : e1.equals(e2))}.)  In other words, two lists are defined to be equal if they
     * contain the same elements in the same order.  This definition ensures that the equals method works properly
     * across different implementations of the {@code List} interface.
     *
     * @param   other   the object to be compared for equality with this list
     * @return  {@code true} if the specified object is equal to this list
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
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < length; i++)
            result = 31 * result + Objects.hashCode(array[i]);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <TT> ImmutableList<TT> emptyList() {
        return new ImmutableList<>((TT[])emptyArray);
    }

    public static <TT> ImmutableList<TT> listOf(TT[] array) {
        return array.length == 0 ? emptyList() : new ImmutableList<>(array, array.length);
    }

    public static <TT> ImmutableList<TT> listOf(TT[] array, int length) {
        return length == 0 ? emptyList() : new ImmutableList<>(array, length);
    }

    // all modifying operations result in UnsupportedOperationException

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

}
