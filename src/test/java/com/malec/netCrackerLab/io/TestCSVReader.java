package com.malec.netCrackerLab.io;

import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestCSVReader {
    @Test
    public void testReadLines() throws URISyntaxException {
        CSVReader reader = new CSVReader(Reader.getFileFromResource("tableOfContents.csv"));
        String[] lines = reader.readLines();

        assertEquals("internet, 0,  123,    456,    0, name,    0,    male,   1234, 56789,  111", lines[0]);
    }

    @Test
    public void testNotFound() {
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> Reader.getFileFromResource("noFile.csv")
        );

        assertTrue(failure.getMessage().contains("File not found"));
    }

}
