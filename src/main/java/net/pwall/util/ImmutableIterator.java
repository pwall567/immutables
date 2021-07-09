package net.pwall.util;

import java.util.Iterator;

public class ImmutableIterator<T> extends ImmutableIteratorBase<T> implements Iterator<T> {

    public ImmutableIterator(T[] array, int length, int index) {
        super(array, length, index);
    }

    @Override
    public T next() {
        return checkNext();
    }

}
