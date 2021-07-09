package net.pwall.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ImmutableMap<K, V> extends ImmutableBase<ImmutableMap.MapEntry<K, V>> implements Map<K, V> {

    public static MapEntry<?, ?>[] emptyEntryArray = (MapEntry<?, ?>[])Array.newInstance(MapEntry.class, 0);

    public ImmutableMap(MapEntry<K, V>[] array, int length) {
        super(array, length); // NOTE: does not check for duplicate keys
    }

    public ImmutableMap(MapEntry<K, V>[] array) {
        this(array, array.length);
    }

    @Override
    public boolean containsKey(Object key) {
        return findKey(array, length, key) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            for (int i = 0; i < length; i++)
                if (array[i].getValue() == null)
                    return true;
        }
        else {
            for (int i = 0; i < length; i++)
                if (value.equals(array[i].getValue()))
                    return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int i = findKey(array, length, key);
        return i >= 0 ? array[i].getValue() : null;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet<>(array, length);
    }

    @Override
    public Collection<V> values() {
        return new ValueCollection<>(array, length);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ImmutableSet<>(array, length);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Map))
            return false;
        Map<?, ?> otherMap = (Map<?, ?>)other;
        if (length != otherMap.size())
            return false;
        for (int i = 0; i < length; i++) {
            MapEntry<?, ?> entry = array[i];
            if (!Objects.equals(otherMap.get(entry.getKey()), entry.getValue()))
                return true;
        }
        return true;
    }

    /**
     * Returns the hash code value for this map.  The hash code of a map is defined to be the sum of the hash codes of
     * each entry in the map's {@code entrySet()} view.  This ensures that {@code m1.equals(m2)} implies that
     * {@code m1.hashCode() == m2.hashCode()} for any two maps {@code m1} and {@code m2}, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return  the hash code value for this map
     */
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < length; i++)
            result += array[i].hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (length == 0)
            return "{}";
        StringBuilder sb = new StringBuilder(length * 16);
        sb.append('{');
        int i = 0;
        while (true) {
            MapEntry<K, V> entry = array[i];
            K key = entry.getKey();
            V value = entry.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (++i >= length)
                break;
            sb.append(',').append(' ');
        }
        sb.append('}');
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <KK, VV> ImmutableMap<KK, VV> from(List<MapEntry<KK, VV>> list) {
        return list.isEmpty() ? emptyMap() : new ImmutableMap<>(list.toArray(new MapEntry[0]));
    }

    @SuppressWarnings("unchecked")
    public static <KK, VV> ImmutableMap<KK, VV> emptyMap() {
        return new ImmutableMap<>((MapEntry<KK, VV>[])emptyEntryArray);
    }

    public static <KK, VV> ImmutableMap<KK, VV> mapOf(MapEntry<KK, VV>[] array) {
        return array.length == 0 ? emptyMap() : new ImmutableMap<>(array, array.length);
    }

    public static <KK, VV> ImmutableMap<KK, VV> mapOf(MapEntry<KK, VV>[] array, int length) {
        return length == 0 ? emptyMap() : new ImmutableMap<>(array, length);
    }

    public static <KK, VV> MapEntry<KK, VV> entry(KK key, VV value) {
        return new MapEntry<>(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <KK, VV> MapEntry<KK, VV>[] createArray(int length) {
        return new MapEntry[length];
    }

    public static <KK, VV> boolean containsKey(MapEntry<KK, VV>[] array, int length, KK key) {
        return findKey(array, length, key) >= 0;
    }

    public static <KK, VV> boolean containsKey(List<MapEntry<KK, VV>> list, KK key) {
        for (MapEntry<KK, VV> entry : list)
            if (Objects.equals(entry.getKey(), key))
                return true;
        return false;
    }

    public static <KK, VV> int findKey(MapEntry<KK, VV>[] array, int length, Object key) {
        if (key == null) {
            for (int i = 0; i < length; i++)
                if (array[i].getKey() == null)
                    return i;
        }
        else {
            for (int i = 0; i < length; i++)
                if (key.equals(array[i].getKey()))
                    return i;
        }
        return -1;
    }

    // all modifying operations result in UnsupportedOperationException

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    public static class MapEntry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;
            if (!(other instanceof Map.Entry))
                return false;
            Entry<?, ?> otherMapEntry = (Entry<?, ?>)other;
            return Objects.equals(key, otherMapEntry.getKey()) && Objects.equals(value, otherMapEntry.getValue());
        }

        /**
         * This just happens to match exactly the hash calculation used by {@link java.util.HashMap}.
         *
         * @return  the hash code
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return String.valueOf(key) + '=' + value;
        }

    }

    public static class KeySet<K, V> extends ImmutableCollectionBase<MapEntry<K, V>, K> implements Set<K> {

        public KeySet(MapEntry<K, V>[] array, int length) {
            super(array, length);
        }

        @Override
        public boolean contains(Object o) {
            if (o == null) {
                for (int i = 0; i < length; i++)
                    if (array[i].getKey() == null)
                        return true;
            }
            else {
                for (int i = 0; i < length; i++)
                    if (o.equals(array[i].getKey()))
                        return true;
            }
            return false;
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator<>(array, length);
        }

        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[length];
            for (int i = 0; i < length; i++)
                newArray[i] = array[i].getKey();
            return newArray;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < length) {
                T[] newArray = (T[])Array.newInstance(a.getClass().getComponentType(), length);
                copyKeys(newArray);
                return newArray;
            }
            copyKeys(a);
            if (a.length > length)
                a[length] = null;
            return a;
        }

        @SuppressWarnings("unchecked")
        private <T> void copyKeys(T[] target) {
            for (int i = 0; i < length; i++)
                target[i] = (T)array[i].getKey();
        }

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
                if (!otherSet.contains(array[i].getKey()))
                    return false;
            return true;
        }

        /**
         * Returns the hash code value for this set.  The hash code of a set is defined to be the sum of the hash codes
         * of the elements in the set, where the hash code of a {@code null} element is defined to be zero.  This
         * ensures that {@code s1.equals(s2)} implies that {@code s1.hashCode() == s2.hashCode()} for any two sets
         * {@code s1} and {@code s2}, as required by the general contract of {@link Object#hashCode}.
         *
         * @return      the hash code value for this set
         */
        public int hashCode() {
            int result = 0;
            for (int i = 0; i < length; i++)
                result += Objects.hashCode(array[i].getKey());
            return result;
        }

    }

    public static class KeyIterator<K, V> extends ImmutableIteratorBase<MapEntry<K, V>> implements Iterator<K> {

        public KeyIterator(MapEntry<K, V>[] array, int length) {
            super(array, length, 0);
        }

        @Override
        public K next() {
            return checkNext().getKey();
        }

    }

    public static class ValueCollection<K, V> extends ImmutableCollectionBase<MapEntry<K, V>, V>
            implements Collection<V> {

        public ValueCollection(MapEntry<K, V>[] array, int length) {
            super(array, length);
        }

        @Override
        public boolean contains(Object o) {
            if (o == null) {
                for (int i = 0; i < length; i++)
                    if (array[i].getValue() == null)
                        return true;
            }
            else {
                for (int i = 0; i < length; i++)
                    if (o.equals(array[i].getValue()))
                        return true;
            }
            return false;
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator<>(array, length);
        }

        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[length];
            for (int i = 0; i < length; i++)
                newArray[i] = array[i].getValue();
            return newArray;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < length) {
                T[] newArray = (T[])Array.newInstance(a.getClass().getComponentType(), length);
                copyValues(newArray);
                return newArray;
            }
            copyValues(a);
            if (a.length > length)
                a[length] = null;
            return a;
        }

        @SuppressWarnings("unchecked")
        private <T> void copyValues(T[] target) {
            for (int i = 0; i < length; i++)
                target[i] = (T)array[i].getValue();
        }

    }

    public static class ValueIterator<K, V> extends ImmutableIteratorBase<MapEntry<K, V>>
            implements Iterator<V> {

        public ValueIterator(MapEntry<K, V>[] array, int length) {
            super(array, length, 0);
        }

        @Override
        public V next() {
            return checkNext().getValue();
        }

    }

}
