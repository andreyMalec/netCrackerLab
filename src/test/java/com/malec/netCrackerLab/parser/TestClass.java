package com.malec.netCrackerLab.parser;

public class TestClass {
    public int testInt;
    public String testString;
    public TestClass2 testClass2;
    public TestClass3 testClass3;
    public TestClass4 testClass4;

    public TestClass(int testInt, String testString, TestClass2 testClass2, TestClass3 testClass3, TestClass4 testClass4) {
        this.testInt = testInt;
        this.testString = testString;
        this.testClass2 = testClass2;
        this.testClass3 = testClass3;
        this.testClass4 = testClass4;
    }
}

class TestClass2 {
    public long testLong;
    public double testDouble;
    public float testFloat;
    public TestClass3 testClass31;

    public TestClass2(long testLong, double testDouble, float testFloat, TestClass3 testClass31) {
        this.testLong = testLong;
        this.testDouble = testDouble;
        this.testFloat = testFloat;
        this.testClass31 = testClass31;
    }
}

class TestClass3 {
    public byte testByte;
    public short testShort;
    public TestClass4 testClass4;

    public TestClass3(byte testByte, short testShort, TestClass4 testClass4) {
        this.testByte = testByte;
        this.testShort = testShort;
        this.testClass4 = testClass4;
    }
}

class TestClass4 {
    public char testChar;
    public boolean testBoolean;

    public TestClass4(char testChar, boolean testBoolean) {
        this.testChar = testChar;
        this.testBoolean = testBoolean;
    }
}
