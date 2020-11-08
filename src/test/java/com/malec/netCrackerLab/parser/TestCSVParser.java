package com.malec.netCrackerLab.parser;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.Gender;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestCSVParser {
    private final ClassParser parser = new CSVParser();

    @Test
    public void testTestClassParser() {
        TestClass testClass = new TestClass(1, "test", new TestClass2(11, 1.2, 1.3f,
                new TestClass3((byte) 100, (short) 200, new TestClass4('l', true))
        ), new TestClass3((byte) 30, (short) 300, new TestClass4('o', false)),
                new TestClass4('l', false)
        );

        String testClassCSV = parser.to(testClass);
        TestClass testClassConstructed = parser.from(testClassCSV, TestClass.class);

        assertNotNull(testClassConstructed);
        assertEquals(testClass.testInt, testClassConstructed.testInt);
        assertEquals(testClass.testClass3.testShort, testClassConstructed.testClass3.testShort);
    }

    @Test
    public void testContractParser() {
        Contract contract = new Contract(0, 123L, 456L,
                new Client(0, "name", 0L, Gender.FEMALE, 1234, 56789)
        );

        String contractCSV = parser.to(contract);

        Contract contractConstructed = parser.from(contractCSV, Contract.class);

        assertNotNull(contractConstructed);
        assertEquals(contract.getId(), contractConstructed.getId());
        assertEquals(contract.getStartDate(), contractConstructed.getStartDate());
        assertEquals(contract.getClient().getFullName(),
                contractConstructed.getClient().getFullName()
        );
        assertEquals(contract.getClient().getSex(), contractConstructed.getClient().getSex());
    }

    @Test
    public void testClientFromCSV() {
        String csvClient = "0,name,0,MALE,1234,56789";

        Client client = parser.from(csvClient, Client.class);

        assertNotNull(client);
        assertEquals(0, (int) client.getId());
        assertEquals("name", client.getFullName());
    }

    @Test
    public void testClientFromCSVError() {
        String csvClient = "name,MALE";

        Client client = parser.from(csvClient, Client.class);

        assertNull(client);
    }

    @Test
    public void testContractFromCSV() {
        String csvContract = "0,123,456,0,name,0,MALE,1234,56789";

        Contract contract = parser.from(csvContract, Contract.class);

        assertNotNull(contract);
        assertEquals(0, (int) contract.getId());
        assertEquals(123, contract.getStartDate().intValue());
        assertEquals("name", contract.getClient().getFullName());
    }

    @Test
    public void testTestClassFromCSV() {
        String csvTestClass = "0,testClass,12,0.3,3.14,-128,200,c,true,127,600,a,true,t,false";

        TestClass testClass = parser.from(csvTestClass, TestClass.class);

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
