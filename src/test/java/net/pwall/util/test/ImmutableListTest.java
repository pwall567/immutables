package net.pwall.util.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.pwall.util.ImmutableList;

public class ImmutableListTest {

    @Test
    public void shouldCreateImmutableList() {
        String[] array = new String[] { "one", "two", "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        assertEquals(3, list.size());
        assertEquals("one", list.get(0));
        assertEquals("two", list.get(1));
        assertEquals("three", list.get(2));
        assertEquals("[one, two, three]", list.toString());
    }

    @Test
    public void shouldWorkWithNullEntries() {
        String[] array = new String[] { "one", "two", null, "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        assertEquals(4, list.size());
        assertEquals("one", list.get(0));
        assertEquals("two", list.get(1));
        assertNull(list.get(2));
        assertEquals("three", list.get(3));
        assertEquals("[one, two, null, three]", list.toString());
    }

    @Test
    public void shouldCompareWithADifferentList() {
        String[] array = new String[] { "one", "two", "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
        assertEquals(list, arrayList);
        assertEquals(arrayList, list);
        assertEquals(list.hashCode(), arrayList.hashCode());
    }

    @Test
    public void shouldCreateIterator() {
        String[] array = new String[] { "one", "two", "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("one", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("two", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("three", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void shouldRejectMutatingOperations() {
        String[] array = new String[] { "one", "two", "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        assertThrows(UnsupportedOperationException.class, () -> list.add("wrong"));
        assertThrows(UnsupportedOperationException.class, () -> list.add(1, "wrong"));
        assertThrows(UnsupportedOperationException.class, () -> list.set(1, "wrong"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove("one"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(1));
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(new ArrayList<>()));
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(1, new ArrayList<>()));
        assertThrows(UnsupportedOperationException.class, () -> list.removeAll(new ArrayList<String>()));
        assertThrows(UnsupportedOperationException.class, () -> list.retainAll(new ArrayList<String>()));
        assertThrows(UnsupportedOperationException.class, list::clear);
        Iterator<String> iterator = list.iterator();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    public void shouldThrowExceptionOnIndexOutOfRange() {
        String[] array = new String[] { "one", "two", "three" };
        ImmutableList<String> list = new ImmutableList<>(array);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    public void shouldCreateEmptyList() {
        ImmutableList<String> empty = ImmutableList.emptyList();
        assertTrue(empty.isEmpty());
        Iterator<String> iterator = empty.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(IndexOutOfBoundsException.class, () -> empty.get(0));
    }

    @Test
    public void shouldConditionallyCreateEmptyList() {
        String[] array = new String[1];
        array[0] = "nine";
        ImmutableList<String> list1 = ImmutableList.listOf(array, 0);
        assertTrue(list1.isEmpty());
        Iterator<String> iterator = list1.iterator();
        assertFalse(iterator.hasNext());
        assertFalse(list1.contains("nine"));
        assertThrows(IndexOutOfBoundsException.class, () -> list1.get(0));
        ImmutableList<String> list2 = ImmutableList.listOf(array);
        assertEquals(1, list2.size());
        assertEquals("nine", list2.get(0));
        assertTrue(list2.contains("nine"));
        ImmutableList<String> list3 = ImmutableList.listOf(array, 1);
        assertEquals(1, list3.size());
        assertEquals("nine", list3.get(0));
        assertTrue(list3.contains("nine"));
    }

}
