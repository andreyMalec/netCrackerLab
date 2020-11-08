package com.malec.netCrackerLab;

import com.malec.netCrackerLab.io.CSVReader;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.model.MobileContract;
import com.malec.netCrackerLab.model.TVContract;
import com.malec.netCrackerLab.parser.CSVParser;
import com.malec.netCrackerLab.util.Array;

import java.io.File;

public class ContractParser {


    public ContractAdapter parse(File file) {
        ContractAdapter adapter = new ContractAdapter();
        CSVReader reader = new CSVReader(file);
        CSVParser parser = new CSVParser();

        for (String line : reader.readLines()) {
            String[] sourceValues = line.split(",");
            for (int i = 0; i < sourceValues.length; i++)
                sourceValues[i] = sourceValues[i].trim();

            String[] extra = sourceValues[sourceValues.length - 1].split(";");
            String[] normalValues = new String[sourceValues.length + extra.length - 2];
            System.arraycopy(extra, 0, normalValues, 0, extra.length);
            System.arraycopy(sourceValues, 1, normalValues, extra.length, sourceValues.length - 2);

            String contractSource = Array.join(normalValues, ",");
            adapter.add(parser.from(contractSource, parseClass(sourceValues[0])));
        }

        return adapter;
    }

    private Class<? extends Contract> parseClass(String s) {
        switch (s.toLowerCase()) {
            case "internet":
                return InternetContract.class;
            case "mobile":
                return MobileContract.class;
            case "tv":
                return TVContract.class;
        }
        return null;
    }
}
