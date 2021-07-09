package net.pwall.util;

import java.util.NoSuchElementException;

public class ImmutableIteratorBase<T> {

    protected final T[] array;
    protected final int length;
    protected int index;

    public ImmutableIteratorBase(T[] array, int length, int index) {
        this.array = array;
        this.length = length;
        this.index = index;
    }

    public boolean hasNext() {
        return index < length;
    }

    public T checkNext() {
        if (!hasNext())
            throw new NoSuchElementException(String.valueOf(index));
        return array[index++];
    }

}
