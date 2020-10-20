package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.InternetContract;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestContactAdapter {
    @Test
    public void testGetById() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        Contract c = adapter.getById(0);

        assertEquals(0, (int) c.id);
        assertEquals(0L, (long) c.startDate);
        assertEquals(0L, (long) c.endDate);
    }

    @Test
    public void testGetByIndex() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        Contract c = adapter.getByIndex(1);

        assertEquals(1, (int) c.id);
        assertEquals(1L, (long) c.startDate);
        assertEquals(1L, (long) c.endDate);
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

        adapter.insert(new InternetContract(0, 0L, 0L, new Client(), 10), 0);
        Contract c = adapter.getByIndex(0);
        assertEquals(0, (int) c.id);

        adapter.insert(new InternetContract(8, 8L, 8L, new Client(), 80), 0);
        Contract c2 = adapter.getByIndex(0);
        assertEquals(8, (int) c2.id);

        adapter.insert(new InternetContract(9, 9L, 9L, new Client(), 90), 2);
        Contract c3 = adapter.getByIndex(0);
        assertEquals(8, (int) c3.id);

        adapter.insert(new InternetContract(9, 9L, 9L, new Client(), 90), 2);
        Contract c4 = adapter.getByIndex(2);
        assertEquals(9, (int) c4.id);
    }

    @Test
    public void testContains() {
        ContractAdapter adapter = new ContractAdapter();
        Contract contact = new InternetContract(9, 9L, 9L, new Client(), 90);
        Contract contact2 = new InternetContract(1, 1L, 1L, new Client(), 10);
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
        adapter.add(new InternetContract(0, 0L, 0L, new Client(), 10));
        adapter.add(new InternetContract(1, 1L, 1L, new Client(), 11));
        adapter.add(new InternetContract(2, 2L, 2L, new Client(), 12));
        adapter.add(new InternetContract(3, 3L, 3L, new Client(), 13));
    }
}
