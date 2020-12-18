package com.malec.netCrackerLab.di;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TestInject {
    @BeforeClass
    public static void init() {
        Injector.init(TestModule.class);
    }

    @Test
    public void testInject() {
        TestModule.D d = new TestModule.D();
        Injector.inject(d);

        assertNotNull(d.a);
        assertNotNull(d.b);
        assertNotNull(d.c);
        assertNotNull(d.b.a);
        assertNotNull(d.c.a);
        assertNotNull(d.c.b);
        assertNotNull(d.c.b.a);

        assertEquals(d.b, d.c.b);
        assertEquals(d.b.a, d.c.b.a);

        assertNotEquals(d.a, d.b.a);
        assertNotEquals(d.a, d.c.a);
        assertNotEquals(d.b.a, d.c.a);

        assertNotNull(d.listA);
        assertNotNull(d.listSetA);

        assertEquals(2, d.listA.size());
        assertEquals(1, d.listSetA.size());
        assertEquals(2, d.listSetA.get(0).size());
    }
}
