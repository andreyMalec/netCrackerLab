package com.malec.netCrackerLab;

import com.malec.netCrackerLab.io.CSVReader;
import com.malec.netCrackerLab.io.Reader;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.parser.CSVParser;
import com.malec.netCrackerLab.util.AdapterSorter;
import com.malec.netCrackerLab.util.AdapterSorterFactory;
import com.malec.netCrackerLab.util.ConsoleLogger;
import com.malec.netCrackerLab.util.Logger;
import com.malec.netCrackerLab.validator.Condition;
import com.malec.netCrackerLab.validator.Conditions;
import com.malec.netCrackerLab.validator.Validator;
import com.malec.netCrackerLab.validator.ValidatorBuilder;

import java.io.File;
import java.net.URISyntaxException;

import javax.inject.Singleton;

import jProcessor.Module;
import jProcessor.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    public File file() {
        try {
            return Reader.getFileFromResource("tableOfContents.csv");
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Provides
    @Singleton
    public Validator<Contract> validator() {
        ValidatorBuilder<Contract> builder = new ValidatorBuilder<>();
        builder.add(new Condition<>(2, Contract::getId,
                ((expected, actual) -> actual % expected == 0)
        ));
        builder.add(new Condition<>("lera", contract -> contract.getClient().getFullName()));
        builder.add(new Condition<>(9, Conditions.GREATER_THAN_OR_EQUALS, Contract::getId,
                (expected, actual) -> expected >= actual
        ));
        return builder.build();
    }

    @Provides
    @Singleton
    public Reader reader(File file) {
        return new CSVReader(file);
    }

    @Provides
    @Singleton
    public AdapterSorter sorter() {
        return AdapterSorterFactory.getSorter();
    }

    @Provides
    @Singleton
    public CSVParser parser() {
        return new CSVParser();
    }

    @Provides
    @Singleton
    public Logger logger() {
        return new ConsoleLogger();
    }
}
