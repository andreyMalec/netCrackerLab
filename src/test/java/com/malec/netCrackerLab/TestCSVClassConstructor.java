package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.reflect.CSVClassConstructor;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestCSVClassConstructor {
    @Test
    public void testClientFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        String csvClient = "0,name,0,MALE,1234,56789";

        Client client = CSVClassConstructor.from(csvClient, Client.class);

        assertNotNull(client);
        assertEquals(0, (int) client.getId());
        assertEquals("name", client.getFullName());
    }

    @Test
    public void testContractFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        String csvContract = "0,123,456,0,name,0,MALE,1234,56789";

        Contract contract = CSVClassConstructor.from(csvContract, Contract.class);

        assertNotNull(contract);
        assertEquals(0, (int) contract.getId());
        assertEquals(123, contract.getStartDate().intValue());
        assertEquals("name", contract.getClient().getFullName());
    }

    @Test
    public void testTestClassFromCSV() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String csvTestClass = "0,testClass,12,0.3,3.14,-128,200,c,true,127,600,a,true,t,false";

        TestClass testClass = CSVClassConstructor.from(csvTestClass, TestClass.class);

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
