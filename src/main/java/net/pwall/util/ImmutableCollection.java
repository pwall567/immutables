package net.pwall.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ImmutableCollection<T> extends ImmutableCollectionBase<T, T> implements Collection<T> {

    public ImmutableCollection(T[] array, int length) {
        super(array, length);
    }

    public ImmutableCollection(T[] array) {
        this(array, array.length);
    }

    @Override
    public boolean contains(Object o) {
        return contains(array, length, o);
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator<>(array, length, 0);
    }

    @Override
    public Object[] toArray() {
        return length == 0 ? emptyArray : Arrays.copyOf(array, length);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TT> TT[] toArray(TT[] a) {
        if (a.length < length)
            return (TT[])Arrays.copyOf(array, length, a.getClass());
        T[] target = (T[])a;
        System.arraycopy(array, 0, target, 0, length);
        if (target.length > length)
            target[length] = null;
        return a;
    }

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
