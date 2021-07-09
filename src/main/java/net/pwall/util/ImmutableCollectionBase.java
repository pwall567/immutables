package net.pwall.util;

import java.util.Collection;

abstract public class ImmutableCollectionBase<A, E> extends ImmutableBase<A> implements Collection<E> {

    protected ImmutableCollectionBase(A[] array, int length) {
        super(array, length);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c)
            if (!contains(item))
                return false;
        return true;
    }

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

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

}
