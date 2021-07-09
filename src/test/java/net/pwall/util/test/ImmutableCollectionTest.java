package net.pwall.util.test;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.ImmutableCollection;

public class ImmutableCollectionTest {

    @Test
    public void shouldCreateCollection() {
        String[] array = new String[]{"tahi", "rua", "toru", "wha"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        assertEquals(4, collection.size());
        assertTrue(collection.contains("tahi"));
        assertTrue(collection.contains("rua"));
        assertTrue(collection.contains("toru"));
        assertTrue(collection.contains("wha"));
        assertEquals("[tahi, rua, toru, wha]", collection.toString());
    }

    @Test
    public void shouldWorkWithNullEntries() {
        String[] array = new String[]{"tahi", "rua", null, "toru"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        assertEquals(4, collection.size());
        assertTrue(collection.contains("tahi"));
        assertTrue(collection.contains("rua"));
        assertTrue(collection.contains(null));
        assertTrue(collection.contains("toru"));
        assertEquals("[tahi, rua, null, toru]", collection.toString());
    }

    @Test
    public void shouldCreateIterator() {
        String[] array = new String[]{"tahi", "rua", "toru", "wha"};
        ImmutableCollection<String> collection = new ImmutableCollection<>(array);
        Iterator<String> iterator = collection.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("tahi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("rua", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("toru", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("wha", iterator.next());
        assertFalse(iterator.hasNext());
    }

}
