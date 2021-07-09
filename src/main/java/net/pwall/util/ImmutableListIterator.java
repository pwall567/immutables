package net.pwall.util;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ImmutableListIterator<T> extends ImmutableIterator<T> implements ListIterator<T> {

    public ImmutableListIterator(T[] array, int length, int index) {
        super(array, length, index);
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public T previous() {
        if (!hasPrevious())
            throw new NoSuchElementException(String.valueOf(index));
        return array[--index];
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException();
    }

}
