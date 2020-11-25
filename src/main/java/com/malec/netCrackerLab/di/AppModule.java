package com.malec.netCrackerLab.di;

import com.malec.netCrackerLab.io.CSVReader;
import com.malec.netCrackerLab.io.Reader;
import com.malec.netCrackerLab.parser.CSVParser;
import com.malec.netCrackerLab.util.AdapterSorter;
import com.malec.netCrackerLab.util.AdapterSorterFactory;
import com.malec.netCrackerLab.util.ConsoleLogger;
import com.malec.netCrackerLab.util.Logger;

import java.io.File;
import java.net.URISyntaxException;

import javax.inject.Singleton;

public class AppModule {
    @Provides
    @Singleton
    Reader reader(File file) {
        return new CSVReader(file);
    }

    @Provides
    @Singleton
    AdapterSorter sorter() {
        return AdapterSorterFactory.getSorter();
    }

    @Provides
    @Singleton
    CSVParser parser() {
        return new CSVParser();
    }

    @Provides
    @Singleton
    Logger logger() {
        return new ConsoleLogger();
    }

    @Provides
    @Singleton
    File file() throws URISyntaxException {
        return Reader.getFileFromResource("tableOfContents.csv");
    }
}
