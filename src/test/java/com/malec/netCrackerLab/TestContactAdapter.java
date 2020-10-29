package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.Gender;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.util.ArrayAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestContactAdapter {
    private static final Client client = new Client(0, "", 0L, Gender.MALE, 0, 0);

    @Test
    public void testSortFilterMapChain() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ArrayAdapter<Integer> newAdapter = adapter.sort(
                (o1, o2) -> Integer.compare(o1.getId(), o2.getId())
        ).filter(
                (it) -> ((InternetContract)it).getSpeedLimit() == 4
        ).map(
                (it) -> it.getId()
        );

        assertEquals(15, (int) newAdapter.getByIndex(0));
        assertEquals(42, (int) newAdapter.getByIndex(1));
    }

    @Test
    public void testMap() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ArrayAdapter<Integer> mapped = adapter.map((element) -> ((InternetContract) element).getSpeedLimit());
        assertEquals(12, (int) mapped.getByIndex(0));
        assertEquals(4, (int) mapped.getByIndex(1));
        assertEquals(3, (int) mapped.getByIndex(2));
        assertEquals(4, (int) mapped.getByIndex(3));
    }

    @Test
    public void testFilter() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter filtered = adapter.filter((element) -> ((InternetContract) element).getSpeedLimit().equals(4));
        assertNotNull(filtered.getById(42));
        assertNotNull(filtered.getById(15));
    }

    @Test
    public void testBubbleSort() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter sorted = adapter.bubbleSort((first, second) -> Integer.compare(first.getId(), second.getId()));
        assertEquals(0, (int) sorted.getByIndex(0).getId());
        assertEquals(2, (int) sorted.getByIndex(1).getId());
        assertEquals(15, (int) sorted.getByIndex(2).getId());
        assertEquals(42, (int) sorted.getByIndex(3).getId());
    }

    @Test
    public void testQuickSort() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter sorted = adapter.sort((first, second) -> Long.compare(first.getStartDate(), second.getStartDate()));
        assertEquals(15, (int) sorted.getByIndex(0).getId());
        assertEquals(42, (int) sorted.getByIndex(1).getId());
        assertEquals(0, (int) sorted.getByIndex(2).getId());
        assertEquals(2, (int) sorted.getByIndex(3).getId());
    }

    @Test
    public void testGetById() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        Contract c = adapter.getById(0);

        assertEquals(0, (int) c.getId());
    }

    @Test
    public void testGetByIndex() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        Contract c = adapter.getByIndex(1);

        assertEquals(1, (int) c.getId());
    }

    @Test
    public void testRemoveAt() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        adapter.removeAt(2);
        Contract c = adapter.getById(2);
        assertNull(c);

        adapter.removeAt(0);
        Contract c2 = adapter.getById(0);
        assertNull(c2);

        adapter.removeAt(1);
        Contract c3 = adapter.getById(3);
        assertNull(c3);

        adapter.removeAt(0);
        Contract c4 = adapter.getById(1);
        assertNull(c4);
    }

    @Test
    public void testInsert() {
        ContractAdapter adapter = new ContractAdapter();

        adapter.insert(new InternetContract(0, 0L, 0L, client, 10), 0);
        Contract c = adapter.getByIndex(0);
        assertEquals(0, (int) c.getId());

        adapter.insert(new InternetContract(8, 8L, 8L, client, 80), 0);
        Contract c2 = adapter.getByIndex(0);
        assertEquals(8, (int) c2.getId());

        adapter.insert(new InternetContract(9, 9L, 9L, client, 90), 2);
        Contract c3 = adapter.getByIndex(0);
        assertEquals(8, (int) c3.getId());

        adapter.insert(new InternetContract(9, 9L, 9L, client, 90), 2);
        Contract c4 = adapter.getByIndex(2);
        assertEquals(9, (int) c4.getId());
    }

    @Test
    public void testContains() {
        ContractAdapter adapter = new ContractAdapter();
        Contract contact = new InternetContract(9, 9L, 9L, client, 90);
        Contract contact2 = new InternetContract(1, 1L, 1L, client, 10);
        adapter.add(contact);
        assertTrue(adapter.contains(contact));
        assertFalse(adapter.contains(contact2));
    }

    @Test
    public void testClone() {
        ContractAdapter adapter = new ContractAdapter();
        assertNotEquals(adapter, adapter.clone());
    }

    @Test
    public void testRemoveById() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);
        adapter.removeById(1);
        assertNull(adapter.getById(1));
        assertNotNull(adapter.getByIndex(2));
        adapter.removeById(2);
        assertNull(adapter.getById(2));
    }

    private void fill(ContractAdapter adapter) {
        adapter.add(new InternetContract(0, 0L, 0L, client, 10));
        adapter.add(new InternetContract(1, 1L, 1L, client, 11));
        adapter.add(new InternetContract(2, 2L, 2L, client, 12));
        adapter.add(new InternetContract(3, 3L, 3L, client, 13));
    }

    private void fillRandom(ContractAdapter adapter) {
        adapter.add(new InternetContract(2, 22L, 23L, client, 12));
        adapter.add(new InternetContract(42, 4L, 4L, client, 4));
        adapter.add(new InternetContract(0, 9L, 6L, client, 3));
        adapter.add(new InternetContract(15, 2L, 3L, client, 4));
    }
}
