/*
 * @(#) ImmutableMap.java
 *
 * immutables  High-performance immutable collections
 * Copyright (c) 2021 Peter Wall
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
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable implementation of {@link Map}.
 *
 * @author  Peter Wall
 * @param   <K>     the key type
 * @param   <V>     the value type
 */
public class ImmutableMap<K, V> extends ImmutableBase<ImmutableMap.MapEntry<K, V>> implements Map<K, V> {

    public static MapEntry<?, ?>[] emptyEntryArray = (MapEntry<?, ?>[])Array.newInstance(MapEntry.class, 0);

    /**
     * Construct an {@code ImmutableMap} with the given array (of {@link MapEntry}) and length.
     *
     * @param   array       the array of {@link MapEntry}
     * @param   length      the length (the number of array items to be considered part of the map)
     * @throws  IndexOutOfBoundsException if the length is less than 0 or greater than the array length
     */
    public ImmutableMap(MapEntry<K, V>[] array, int length) {
        super(array, checkLength(array, length)); // NOTE: does not check for duplicate keys
    }

    /**
     * Construct an {@code ImmutableList} with the given array (using the entire array).
     *
     * @param   array       the array of {@link MapEntry}
     */
    public ImmutableMap(MapEntry<K, V>[] array) {
        this(array, array.length);
    }

    /**
     * Test whether the map contains a key equal to the specified key (which may be {@code null}).
     *
     * @param   key         the key
     * @return              {@code true} if the map contains the key
     */
    @Override
    public boolean containsKey(Object key) {
        return findKey(array, length, key) >= 0;
    }

    /**
     * Test whether the map contains a value equal to the specified value (which may be {@code null}).
     *
     * @param   value       the value
     * @return              {@code true} if the map contains the value
     */
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

    /**
     * Get to value corresponding to the supplied key.  If the key is not found, {@code null} is returned.
     *
     * @param   key         the key
     * @return              the value
     */
    @Override
    public V get(Object key) {
        int i = findKey(array, length, key);
        return i >= 0 ? array[i].getValue() : null;
    }

    /**
     * Get a {@link Set} of the keys in use in this map.  The set is backed by the original array, and will retain the
     * original order.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<K> keySet() {
        return new KeySet<>(array, length);
    }

    /**
     * Get a {@link Collection} of the values in this map.  The collection is backed by the original array, and will
     * retain the original order.
     *
     * @return              the {@link Collection}
     */
    @Override
    public Collection<V> values() {
        return new ValueCollection<>(array, length);
    }

    /**
     * Get a {@link Set} of the entries in this map.  The set is backed by the original array, and will retain the
     * original order.
     *
     * @return              the {@link Set}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new ImmutableSet<>(array, length);
    }

    /**
     * Compares the specified object with this map for equality.  Returns {@code true} if the given object is also a
     * map, the maps contains the same number of entries and for each key in one map, the values returned by both maps
     * are equal (either both are {@code null}, or they compare as equal using {@link Object#equals}.  This ensures that
     * the {@code equals} method works properly across different implementations of the {@code Map} interface.
     *
     * @param   other           object to be compared for equality with this map
     * @return                  {@code true} if the specified object is equal to this map
     */
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
    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < length; i++)
            result += array[i].hashCode();
        return result;
    }

    /**
     * Returns a string representation of this map.  The string representation consists of the string representations of
     * each of the entries (as key=value), enclosed in braces and separated by a comma and a space.  Keys and values are
     * converted to strings by {@link String#valueOf(Object)}.
     *
     * @return          a string representation of this collection
     */
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

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modifying operation - not allowed.
     *
     * @throws      UnsupportedOperationException (in all cases)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    /**
     * Create an {@code ImmutableMap} from the supplied {@link List} of {@link MapEntry}.
     *
     * @param   list        the {@link List} of {@link MapEntry}
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code ImmutableMap}
     */
    @SuppressWarnings("unchecked")
    public static <KK, VV> ImmutableMap<KK, VV> from(List<MapEntry<KK, VV>> list) {
        return list.isEmpty() ? emptyMap() : new ImmutableMap<>(list.toArray(new MapEntry[0]));
    }

    /**
     * Get an empty {@code ImmutableMap}.  This avoids a memory allocation if the list is empty.
     *
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              an empty map
     */
    @SuppressWarnings("unchecked")
    public static <KK, VV> ImmutableMap<KK, VV> emptyMap() {
        return new ImmutableMap<>((MapEntry<KK, VV>[])emptyEntryArray);
    }

    /**
     * Create an {@code ImmutableMap} from the supplied array of {@link MapEntry}.
     *
     * @param   array       the array of {@link MapEntry}
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code ImmutableMap}
     */
    public static <KK, VV> ImmutableMap<KK, VV> mapOf(MapEntry<KK, VV>[] array) {
        return array.length == 0 ? emptyMap() : new ImmutableMap<>(array, array.length);
    }

    /**
     * Create an {@code ImmutableMap} from the supplied array of {@link MapEntry} with the specified length.
     *
     * @param   array       the array of {@link MapEntry}
     * @param   length      the length (the number of array items to be considered part of the map)
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@code ImmutableMap}
     */
    public static <KK, VV> ImmutableMap<KK, VV> mapOf(MapEntry<KK, VV>[] array, int length) {
        return length == 0 ? emptyMap() : new ImmutableMap<>(array, length);
    }

    /**
     * Create a {@link MapEntry}.
     *
     * @param   key         the key
     * @param   value       the value
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new {@link MapEntry}
     */
    public static <KK, VV> MapEntry<KK, VV> entry(KK key, VV value) {
        return new MapEntry<>(key, value);
    }

    /**
     * Create an array of {@link MapEntry}, of the specified length.
     *
     * @param   length      the length
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the new array
     */
    @SuppressWarnings("unchecked")
    public static <KK, VV> MapEntry<KK, VV>[] createArray(int length) {
        return new MapEntry[length];
    }

    /**
     * Test whether an array of {@link MapEntry} contains the specified key.  The {@code ImmutableMap} constructor does
     * not check for duplicate keys, so this function provides a means of checking prior to construction.
     *
     * @param   array       the array
     * @param   length      the length so far (the number of elements to be checked against)
     * @param   key         the new key
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              {@code true} if the key exists in the array
     */
    public static <KK, VV> boolean containsKey(MapEntry<KK, VV>[] array, int length, KK key) {
        return findKey(array, length, key) >= 0;
    }

    /**
     * Test whether a {@link List} of {@link MapEntry} contains the specified key.  The {@code ImmutableMap} constructor
     * does not check for duplicate keys, so this function provides a means of checking prior to construction.
     *
     * @param   list        the {@link List}
     * @param   key         the new key
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              {@code true} if the key exists in the {@link List}
     */
    public static <KK, VV> boolean containsKey(List<MapEntry<KK, VV>> list, KK key) {
        for (MapEntry<KK, VV> entry : list)
            if (Objects.equals(entry.getKey(), key))
                return true;
        return false;
    }

    /**
     * Locate a key in an array of {@link MapEntry}.  This is used both to check for duplicates prior to construction,
     * and to find a value corresponding to a key in a map.
     *
     * @param   array       the array
     * @param   length      the length so far (the number of elements to be checked against)
     * @param   key         the new key
     * @param   <KK>        the key type
     * @param   <VV>        the value type
     * @return              the index of the key in the array, or -1 if it is not found
     */
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

    /**
     * A map entry (key, value) for an {@code ImmutableMap}.
     *
     * @param   <K>         the key type
     * @param   <V>         the value type
     */
    public static class MapEntry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        /**
         * Construct a {@code MapEntry} with the given key and value.
         *
         * @param   key         the key
         * @param   value       the value
         */
        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Get the key.
         *
         * @return      the key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Get the value.
         *
         * @return      the value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Modifying operation - not allowed.
         *
         * @throws      UnsupportedOperationException (in all cases)
         */
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        /**
         * Compare with another object for equality.  Any other {@link Map.Entry} with the same key and value is
         * considered equal (where the key and value equality tests are done by {@link Objects#equals}.
         *
         * @param   other       the other object
         * @return              {@code true} if the objects are equal
         */
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
         * This is coded to match exactly the hash calculation used by {@link java.util.HashMap}.
         *
         * @return  the hash code
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        /**
         * Create a string representation of this {@code MapEntry}, in the form "key=value".
         *
         * @return      the string representation
         */
        @Override
        public String toString() {
            return String.valueOf(key) + '=' + value;
        }

    }

    /**
     * Implementation of the {@link Set} interface to provide a view of the keys in the {@code ImmutableMap}.  It makes
     * use of the same underlying array, but returns only the key portion of the {@link MapEntry}.
     *
     * @param   <K>         the key type
     * @param   <V>         the value type
     */
    public static class KeySet<K, V> extends ImmutableCollectionBase<MapEntry<K, V>, K> implements Set<K> {

        /**
         * Construct a {@code KeySet} with the given array of {@link MapEntry} and the specified length.
         *
         * @param   array       the array of {@link MapEntry}
         * @param   length      the length (the number of array items to be considered part of the map)
         */
        public KeySet(MapEntry<K, V>[] array, int length) {
            super(array, length);
        }

        /**
         * Test whether the set contains an element equal to the specified object (which may be {@code null}).
         *
         * @param   o           the key
         * @return              {@code true} if the set contains the object
         */
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

        /**
         * Return an {@link Iterator} over the set.
         *
         * @return          the {@link Iterator}
         */
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator<>(array, length);
        }

        /**
         * Create a copy of the set in an array.
         *
         * @return          a copy of the keys in the active portion of the array
         */
        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[length];
            for (int i = 0; i < length; i++)
                newArray[i] = array[i].getKey();
            return newArray;
        }

        /**
         * Return a copy of the set, either in the supplied array (if it fits) or in a new array of the specified type.
         *
         * @param   a       the destination array
         * @param   <T>     the type of the destination array items
         * @return          a copy of the keys in the active portion of the map array, in a new array if necessary
         * @throws  ArrayStoreException if the keys in the map can't be stored in the destination array
         */
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

        /**
         * Copy the keys from the map's array into the supplied array.
         *
         * @param   target      the target array
         * @param   <T>         the array item type
         */
        @SuppressWarnings("unchecked")
        private <T> void copyKeys(T[] target) {
            for (int i = 0; i < length; i++)
                target[i] = (T)array[i].getKey();
        }

        /**
         * Compares the specified object with this set for equality.  Returns {@code true} if the specified object is
         * also a set, the two sets have the same size, and every member of the specified set is contained in this set
         * (or equivalently, every member of this set is contained in the specified set).  This definition ensures that
         * the equals method works properly across different implementations of the set interface.
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
        @Override
        public int hashCode() {
            int result = 0;
            for (int i = 0; i < length; i++)
                result += Objects.hashCode(array[i].getKey());
            return result;
        }

    }

    /**
     * Implementation of {@link Iterator} to iterate over the contents of a {@link KeySet}.
     *
     * @param   <K>         the key type
     * @param   <V>         the value type
     */
    public static class KeyIterator<K, V> extends ImmutableIteratorBase<MapEntry<K, V>> implements Iterator<K> {

        /**
         * Construct a {@code KeyIterator} with the given array (of {@link MapEntry}) and length.
         *
         * @param   array       the array of {@link MapEntry}
         * @param   length      the length (the number of array items to be considered part of the map)
         */
        public KeyIterator(MapEntry<K, V>[] array, int length) {
            super(array, length, 0);
        }

        /**
         * Get the next key referenced by this {@code KeyIterator}.
         *
         * @return          the next key
         * @throws NoSuchElementException  if there is no "next" key
         */
        @Override
        public K next() {
            return checkNext().getKey();
        }

    }

    /**
     * Implementation of the {@link Collection} interface to provide a view of the values in the {@code ImmutableMap}.
     * It makes use of the same underlying array, but returns only the value portion of the {@link MapEntry}.
     *
     * @param   <K>         the key type
     * @param   <V>         the value type
     */
    public static class ValueCollection<K, V> extends ImmutableCollectionBase<MapEntry<K, V>, V>
            implements Collection<V> {

        /**
         * Construct a {@code ValueCollection} with the given array of {@link MapEntry} and the specified length.
         *
         * @param   array       the array of {@link MapEntry}
         * @param   length      the length (the number of array items to be considered part of the map)
         */
        public ValueCollection(MapEntry<K, V>[] array, int length) {
            super(array, length);
        }

        /**
         * Test whether the collection contains an element equal to the specified object (which may be {@code null}).
         *
         * @param   o           the value
         * @return              {@code true} if the collection contains the object
         */
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

        /**
         * Return an {@link Iterator} over the collection.
         *
         * @return          the {@link Iterator}
         */
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator<>(array, length);
        }

        /**
         * Create a copy of the collection in an array.
         *
         * @return          a copy of the values in the active portion of the array
         */
        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[length];
            for (int i = 0; i < length; i++)
                newArray[i] = array[i].getValue();
            return newArray;
        }

        /**
         * Return a copy of the collection, either in the supplied array (if it fits) or in a new array of the specified
         * type.
         *
         * @param   a       the destination array
         * @param   <T>     the type of the destination array items
         * @return          a copy of the values in the active portion of the map array, in a new array if necessary
         * @throws  ArrayStoreException if the values in the map can't be stored in the destination array
         */
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

        /**
         * Copy the values from the map's array into the supplied array.
         *
         * @param   target      the target array
         * @param   <T>         the array item type
         */
        @SuppressWarnings("unchecked")
        private <T> void copyValues(T[] target) {
            for (int i = 0; i < length; i++)
                target[i] = (T)array[i].getValue();
        }

    }

    /**
     * Implementation of {@link Iterator} to iterate over the contents of a {@link ValueCollection}.
     *
     * @param   <K>         the key type
     * @param   <V>         the value type
     */
    public static class ValueIterator<K, V> extends ImmutableIteratorBase<MapEntry<K, V>> implements Iterator<V> {

        /**
         * Construct a {@code ValueIterator} with the given array (of {@link MapEntry}) and length.
         *
         * @param   array       the array of {@link MapEntry}
         * @param   length      the length (the number of array items to be considered part of the map)
         */
        public ValueIterator(MapEntry<K, V>[] array, int length) {
            super(array, length, 0);
        }

        /**
         * Get the next value referenced by this {@code ValueIterator}.
         *
         * @return          the next value
         * @throws NoSuchElementException  if there is no "next" value
         */
        @Override
        public V next() {
            return checkNext().getValue();
        }

    }

}
