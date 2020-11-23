package com.malec.netCrackerLab;

import com.malec.netCrackerLab.io.CSVReader;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.model.MobileContract;
import com.malec.netCrackerLab.model.TVContract;
import com.malec.netCrackerLab.parser.CSVParser;
import com.malec.netCrackerLab.util.Array;
import com.malec.netCrackerLab.validator.ValidationResult;
import com.malec.netCrackerLab.validator.Validator;

import java.io.File;

public class ContractParser {
    public ContractAdapter parse(File file, Validator<Contract> validator) {
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
            Contract contract = parser.from(contractSource, parseClass(sourceValues[0]));
            if (validator != null) {
                int errorCount = 0;
                for (ValidationResult result : validator.validate(contract)) {
                    if (!result.isValid())
                        errorCount++;
                    System.out.println(result.getMessage());
                }
                System.out.print("Validation finished with ");
                System.out.print(errorCount);
                System.out.print(" error");
                if (errorCount != 1)
                    System.out.print("s");
                if (errorCount > 0) {
                    System.out.println();
                    System.out.print("Current contract [");
                    System.out.print(contract);
                    System.out.print("] will be ignored");
                    System.out.println();
                } else {
                    adapter.add(contract);
                    System.out.println();
                }
                System.out.println();
            } else
                adapter.add(contract);
        }

        return adapter;
    }

    public ContractAdapter parse(File file) {
        return parse(file, null);
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
