package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.Gender;
import com.malec.netCrackerLab.reflect.CSVParser;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestCSVParser {
    @Test
    public void testTestClassParser() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        TestClass testClass = new TestClass(1, "test", new TestClass2(11, 1.2, 1.3f,
                new TestClass3((byte) 100, (short) 200, new TestClass4('l', true))
        ), new TestClass3((byte) 30, (short) 300, new TestClass4('o', false)),
                new TestClass4('l', false)
        );

        String testClassCSV = CSVParser.toCSV(testClass);
        TestClass testClassConstructed = CSVParser.from(testClassCSV, TestClass.class);

        assertEquals(testClass.testInt, testClassConstructed.testInt);
        assertEquals(testClass.testClass3.testShort, testClassConstructed.testClass3.testShort);
    }

    @Test
    public void testContractParser() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Contract contract = new Contract(0, 123L, 456L,
                new Client(0, "name", 0L, Gender.FEMALE, 1234, 56789)
        );

        String contractCSV = CSVParser.toCSV(contract);

        Contract contractConstructed = CSVParser.from(contractCSV, Contract.class);

        assertEquals(contract.getId(), contractConstructed.getId());
        assertEquals(contract.getStartDate(), contractConstructed.getStartDate());
        assertEquals(contract.getClient().getFullName(),
                contractConstructed.getClient().getFullName()
        );
    }

    @Test
    public void testClientFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String csvClient = "0,name,0,MALE,1234,56789";

        Client client = CSVParser.from(csvClient, Client.class);

        assertNotNull(client);
        assertEquals(0, (int) client.getId());
        assertEquals("name", client.getFullName());
    }

    @Test
    public void testContractFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String csvContract = "0,123,456,0,name,0,MALE,1234,56789";

        Contract contract = CSVParser.from(csvContract, Contract.class);

        assertNotNull(contract);
        assertEquals(0, (int) contract.getId());
        assertEquals(123, contract.getStartDate().intValue());
        assertEquals("name", contract.getClient().getFullName());
    }

    @Test
    public void testTestClassFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String csvTestClass = "0,testClass,12,0.3,3.14,-128,200,c,true,127,600,a,true,t,false";

        TestClass testClass = CSVParser.from(csvTestClass, TestClass.class);

        assertNotNull(testClass);
        assertEquals(0, testClass.testInt);
        assertEquals("testClass", testClass.testString);
        assertEquals(12, testClass.testClass2.testLong);
        assertEquals(0.3, testClass.testClass2.testDouble, 0.1);
        assertEquals(3.14f, testClass.testClass2.testFloat, 0.1f);
        assertEquals(-128, testClass.testClass2.testClass31.testByte);
        assertEquals(200, testClass.testClass2.testClass31.testShort);
        assertEquals('c', testClass.testClass2.testClass31.testClass4.testChar);
        assertTrue(testClass.testClass2.testClass31.testClass4.testBoolean);
        assertEquals(127, testClass.testClass3.testByte);
        assertEquals(600, testClass.testClass3.testShort);
        assertEquals('a', testClass.testClass3.testClass4.testChar);
        assertTrue(testClass.testClass3.testClass4.testBoolean);
        assertEquals('t', testClass.testClass4.testChar);
        assertFalse(testClass.testClass4.testBoolean);
    }
}
