package com.malec.netCrackerLab;

import com.malec.netCrackerLab.util.AdapterSorterFactory;
import com.malec.netCrackerLab.util.ArrayAdapter;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestArrayAdapter {
    @Test
    public void testSortFilterMapChain() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fillRandom(adapter);

        ArrayAdapter<Integer> newAdapter = adapter
                .sorted(AdapterSorterFactory.getSorter(), Comparator.naturalOrder())
                .filter(it -> it.contains("e")).map(it -> it.indexOf("t"));

        assertEquals(4, (int) newAdapter.getByIndex(0));
        assertEquals(0, (int) newAdapter.getByIndex(1));
        assertEquals(2, newAdapter.getSize());
    }

    @Test
    public void testMap() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fill(adapter);

        ArrayAdapter<Integer> mapped = adapter.map(Integer::valueOf);
        assertEquals(1, (int) mapped.getByIndex(0));
        assertEquals(2, (int) mapped.getByIndex(1));
        assertEquals(3, (int) mapped.getByIndex(2));
        assertEquals(4, (int) mapped.getByIndex(3));
    }

    @Test
    public void testFilter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fillRandom(adapter);

        ArrayAdapter<String> filtered = adapter.filter(it -> it.contains("a"));
        assertEquals("testCase", filtered.getByIndex(0));
        assertEquals("abc", filtered.getByIndex(1));
        assertEquals("random", filtered.getByIndex(2));
        assertEquals(3, filtered.getSize());
    }

    @Test
    public void testBubbleSort() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fillRandom(adapter);

        ArrayAdapter<String> sorted = adapter.sorted(AdapterSorterFactory.getBubbleSorter(),
                Comparator.comparingInt(it -> it.charAt(0))
        );
        assertEquals("abc", sorted.getByIndex(0));
        assertEquals("qwerty", sorted.getByIndex(1));
        assertEquals("random", sorted.getByIndex(2));
        assertEquals("testCase", sorted.getByIndex(3));
    }

    @Test
    public void testQuickSort() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fill(adapter);
        adapter.swap(0, 2);
        adapter.swap(1, 2);

        ArrayAdapter<String> sorted = adapter.sorted(AdapterSorterFactory.getSorter(),
                Comparator.comparingInt(it -> it.charAt(0))
        );
        assertEquals("1", sorted.getByIndex(0));
        assertEquals("2", sorted.getByIndex(1));
        assertEquals("3", sorted.getByIndex(2));
        assertEquals("4", sorted.getByIndex(3));
    }

    @Test
    public void testConstructor() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fill(adapter);

        ArrayAdapter<String> anotherAdapter = new ArrayAdapter<>(adapter);
        assertEquals("1", anotherAdapter.getByIndex(0));
    }

    @Test
    public void testGetByIndex() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fill(adapter);

        assertEquals("3", adapter.getByIndex(2));

        IndexOutOfBoundsException failure = assertThrows(IndexOutOfBoundsException.class,
                () -> adapter.getByIndex(10)
        );
        assertEquals("Index: 10, Size: 4", failure.getMessage());
    }

    @Test
    public void testRemoveAt() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>();
        fill(adapter);

        String rm = adapter.removeAt(2);
        assertNotNull(rm);

        IndexOutOfBoundsException failure = assertThrows(IndexOutOfBoundsException.class,
                () -> adapter.removeAt(10)
        );
        assertEquals("Index: 10, Size: 3", failure.getMessage());
    }

    @Test
    public void testInsert() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();

        adapter.insert("123", 0);
        assertEquals("123", adapter.getByIndex(0));

        IndexOutOfBoundsException failure = assertThrows(IndexOutOfBoundsException.class,
                () -> adapter.insert("123", 10)
        );
        assertEquals("Index: 10, Size: 1", failure.getMessage());

        adapter.insert("456", 1);
        assertEquals("123", adapter.getByIndex(0));
        assertEquals("456", adapter.getByIndex(1));

        adapter.insert("789", 1);
        assertEquals("123", adapter.getByIndex(0));
        assertEquals("789", adapter.getByIndex(1));
        assertEquals("456", adapter.getByIndex(2));
    }

    @Test
    public void testContains() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fillRandom(adapter);

        String s1 = "test";
        String s2 = "abc";

        assertFalse(adapter.contains(s1));
        assertTrue(adapter.contains(s2));
    }

    @Test
    public void testClone() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>();
        fill(adapter);
        ArrayAdapter<String> clone = adapter.clone();

        assertNotEquals(adapter, clone);
        assertEquals(adapter.getByIndex(0), clone.getByIndex(0));
        clone.removeAt(1);
        assertNotEquals(adapter.getByIndex(1), clone.getByIndex(1));
    }

    private void fill(ArrayAdapter<String> adapter) {
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("4");
    }

    private void fillRandom(ArrayAdapter<String> adapter) {
        adapter.add("testCase");
        adapter.add("abc");
        adapter.add("qwerty");
        adapter.add("random");
    }
}
