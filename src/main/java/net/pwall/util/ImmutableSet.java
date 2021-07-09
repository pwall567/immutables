package net.pwall.util;

import java.util.Objects;
import java.util.Set;

public class ImmutableSet<T> extends ImmutableCollection<T> implements Set<T> {

    public ImmutableSet(T[] array, int length) {
        super(array, length);
    }

    public ImmutableSet(T[] array) {
        this(array, array.length);
    }

    /**
     * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is also a
     * set, the two sets have the same size, and every member of the specified set is contained in this set (or
     * equivalently, every member of this set is contained in the specified set).  This definition ensures that the
     * equals method works properly across different implementations of the set interface.
     *
     * @param   other   object to be compared for equality with this set
     * @return  {@code true} if the specified object is equal to this set
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Set))
            return false;
        Set<?> otherSet = (Set<?>)other;
        if (length != otherSet.size())
            return false;
        for (int i = 0; i < length; i++)
            if (!otherSet.contains(array[i]))
                return false;
        return true;
    }

    /**
     * Returns the hash code value for this set.  The hash code of a set is defined to be the sum of the hash codes of
     * the elements in the set, where the hash code of a {@code null} element is defined to be zero.  This ensures that
     * {@code s1.equals(s2)} implies that {@code s1.hashCode() == s2.hashCode()} for any two sets {@code s1} and
     * {@code s2}, as required by the general contract of {@link Object#hashCode}.
     *
     * @return  the hash code value for this set
     */
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < length; i++)
            result += Objects.hashCode(array[i]);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <TT> ImmutableSet<TT> emptySet() {
        return new ImmutableSet<>((TT[])emptyArray);
    }

    public static <TT> ImmutableSet<TT> setOf(TT[] array) {
        return array.length == 0 ? emptySet() : new ImmutableSet<>(array, array.length);
    }

    public static <TT> ImmutableSet<TT> setOf(TT[] array, int length) {
        return length == 0 ? emptySet() : new ImmutableSet<>(array, length);
    }

}
