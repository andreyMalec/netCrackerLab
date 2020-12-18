package com.malec.netCrackerLab;

import com.malec.netCrackerLab.di.Injector;
import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.Gender;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.model.MobileContract;
import com.malec.netCrackerLab.util.ArrayAdapter;
import com.malec.netCrackerLab.util.BubbleSorter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestContactAdapter {
    private static final Client client = new Client(0, "", 0L, Gender.MALE, 0, 0);

    @BeforeClass
    public static void init() {
        Injector.init(AppModule.class);
    }

    @Test
    public void testValidate() {
        ContractParser parser = new ContractParser();
        Injector.inject(parser);

        ContractAdapter adapter = parser.parse();

        assertNull(adapter.getById(0));
        assertNull(adapter.getById(1));
        assertNull(adapter.getById(2));
        assertNull(adapter.getById(3));
        assertNull(adapter.getById(4));
        assertNotNull(adapter.getById(8));
        assertNull(adapter.getById(10));
    }

    @Test
    public void testFromFile() {
        ContractParser parser = new ContractParser();
        Injector.inject(parser);
        parser.validator = null;
        ContractAdapter adapter = parser.parse();

        for (int i = 0; i < adapter.size(); i++)
            assertNotNull(adapter.getByIndex(i));

        assertEquals(InternetContract.class, adapter.getByIndex(0).getClass());
        assertEquals(111, (int) ((InternetContract) adapter.getByIndex(0)).getSpeedLimit());

        assertEquals(MobileContract.class, adapter.getByIndex(1).getClass());
        assertEquals(23, (int) ((MobileContract) adapter.getByIndex(1)).getGb());
    }

    @Test
    public void testSortFilterMapChain() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ArrayAdapter<Integer> newAdapter = adapter.sorted(Comparator.comparingInt(Contract::getId))
                .filter(it -> ((InternetContract) it).getSpeedLimit() == 4).map(Contract::getId);

        assertEquals(15, (int) newAdapter.getByIndex(0));
        assertEquals(42, (int) newAdapter.getByIndex(1));
    }

    @Test
    public void testMap() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ArrayAdapter<Integer> mapped = adapter.map(element -> ((InternetContract) element).getSpeedLimit());
        assertEquals(12, (int) mapped.getByIndex(0));
        assertEquals(4, (int) mapped.getByIndex(1));
        assertEquals(3, (int) mapped.getByIndex(2));
        assertEquals(4, (int) mapped.getByIndex(3));
    }

    @Test
    public void testFilter() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter filtered = adapter
                .filter(element -> ((InternetContract) element).getSpeedLimit().equals(4));
        assertNotNull(filtered.getById(42));
        assertNotNull(filtered.getById(15));
    }

    @Test
    public void testBubbleSort() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter sorted = adapter.sorted(new BubbleSorter(), Comparator.comparingInt(Contract::getId));
        assertEquals(0, (int) sorted.getByIndex(0).getId());
        assertEquals(2, (int) sorted.getByIndex(1).getId());
        assertEquals(15, (int) sorted.getByIndex(2).getId());
        assertEquals(42, (int) sorted.getByIndex(3).getId());
    }

    @Test
    public void testQuickSort() {
        ContractAdapter adapter = new ContractAdapter();
        Injector.inject(adapter);
        fillRandom(adapter);

        ContractAdapter sorted = adapter.sorted(Comparator.comparingLong(Contract::getStartDate));
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
        assertNull(adapter.getById(10));
    }

    @Test
    public void testRemoveById() {
        ContractAdapter adapter = new ContractAdapter();
        fill(adapter);

        Contract removed = adapter.removeById(1);
        assertNotNull(removed);
        assertNull(adapter.getById(1));
        assertNotNull(adapter.getByIndex(2));

        Contract removed2 = adapter.removeById(8);
        assertNull(removed2);
        assertNull(adapter.getById(8));

        assertEquals(3, adapter.size());
    }

    @Test
    public void testCloneConstructor() {
        ContractAdapter adapter = new ContractAdapter();
        ContractAdapter anotherAdapter = new ContractAdapter(adapter);

        assertEquals(adapter.sorter, anotherAdapter.sorter);
    }

    private void fill(ContractAdapter adapter) {
        adapter.add(new InternetContract(10, 0, 0L, 0L, client));
        adapter.add(new InternetContract(11, 1, 1L, 1L, client));
        adapter.add(new InternetContract(12, 2, 2L, 2L, client));
        adapter.add(new InternetContract(13, 3, 3L, 3L, client));
    }

    private void fillRandom(ContractAdapter adapter) {
        adapter.add(new InternetContract(12, 2, 22L, 23L, client));
        adapter.add(new InternetContract(4, 42, 4L, 4L, client));
        adapter.add(new InternetContract(3, 0, 9L, 6L, client));
        adapter.add(new InternetContract(4, 15, 2L, 3L, client));
    }
}
