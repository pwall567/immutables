package net.pwall.util;

import java.util.Map;
import java.util.Objects;

public class ImmutableMapEntry<K, V> implements Map.Entry<K, V> {

    private final K key;
    private final V value;

    /**
     * Construct an {@code ImmutableMapEntry} with the given key and value.
     *
     * @param   key         the key
     * @param   value       the value
     */
    public ImmutableMapEntry(K key, V value) {
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
        Map.Entry<?, ?> otherMapEntry = (Map.Entry<?, ?>)other;
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
     * Create a string representation of this {@code ImmutableMapEntry}, in the form "key=value".
     *
     * @return      the string representation
     */
    @Override
    public String toString() {
        return String.valueOf(key) + '=' + value;
    }

}
