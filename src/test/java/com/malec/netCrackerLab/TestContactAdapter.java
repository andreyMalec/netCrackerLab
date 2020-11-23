package com.malec.netCrackerLab;

import com.malec.netCrackerLab.io.Reader;
import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.Gender;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.model.MobileContract;
import com.malec.netCrackerLab.util.AdapterSorterFactory;
import com.malec.netCrackerLab.util.ArrayAdapter;
import com.malec.netCrackerLab.validator.Condition;
import com.malec.netCrackerLab.validator.Conditions;
import com.malec.netCrackerLab.validator.Validator;

import org.junit.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestContactAdapter {
    private static final Client client = new Client(0, "", 0L, Gender.MALE, 0, 0);

    @Test
    public void testValidate() throws URISyntaxException {
        ContractParser parser = new ContractParser();

        List<Condition<Contract>> conditions = new ArrayList<>();
        conditions.add(new Condition<>(2, Contract::getId,
                ((expected, actual) -> actual % expected == 0)
        ));
        conditions.add(new Condition<>("lera", contract -> contract.getClient().getFullName()));
        conditions.add(new Condition<>(9, Conditions.GREATER_THAN_OR_EQUALS, Contract::getId,
                (expected, actual) -> expected >= actual
        ));
        Validator<Contract> validator = new Validator<>(conditions);

        ContractAdapter adapter = parser
                .parse(Reader.getFileFromResource("tableOfContents.csv"), validator);

        assertNull(adapter.getById(0));
        assertNull(adapter.getById(1));
        assertNull(adapter.getById(2));
        assertNull(adapter.getById(3));
        assertNull(adapter.getById(4));
        assertNotNull(adapter.getById(8));
        assertNull(adapter.getById(10));
    }

    @Test
    public void testFromFile() throws URISyntaxException {
        ContractParser parser = new ContractParser();
        ContractAdapter adapter = parser.parse(Reader.getFileFromResource("tableOfContents.csv"));

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

        ArrayAdapter<Integer> newAdapter = adapter
                .sorted(AdapterSorterFactory.getSorter(), Comparator.comparingInt(Contract::getId))
                .filter(it -> ((InternetContract) it).getSpeedLimit() == 4).map(Contract::getId);

        assertEquals(15, (int) newAdapter.getByIndex(0));
        assertEquals(42, (int) newAdapter.getByIndex(1));
    }

    @Test
    public void testMap() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ArrayAdapter<Integer> mapped = adapter
                .map(element -> ((InternetContract) element).getSpeedLimit());
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

        ContractAdapter sorted = adapter.sorted(AdapterSorterFactory.getBubbleSorter(),
                Comparator.comparingInt(Contract::getId)
        );
        assertEquals(0, (int) sorted.getByIndex(0).getId());
        assertEquals(2, (int) sorted.getByIndex(1).getId());
        assertEquals(15, (int) sorted.getByIndex(2).getId());
        assertEquals(42, (int) sorted.getByIndex(3).getId());
    }

    @Test
    public void testQuickSort() {
        ContractAdapter adapter = new ContractAdapter();
        fillRandom(adapter);

        ContractAdapter sorted = adapter.sorted(AdapterSorterFactory.getSorter(),
                Comparator.comparingLong(Contract::getStartDate)
        );
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
