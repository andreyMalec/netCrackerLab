package com.malec.netCrackerLab;

import com.malec.netCrackerLab.di.Injector;
import com.malec.netCrackerLab.io.Reader;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.model.InternetContract;
import com.malec.netCrackerLab.model.MobileContract;
import com.malec.netCrackerLab.model.TVContract;
import com.malec.netCrackerLab.parser.CSVParser;
import com.malec.netCrackerLab.util.Array;
import com.malec.netCrackerLab.util.Logger;
import com.malec.netCrackerLab.validator.ValidationResult;
import com.malec.netCrackerLab.validator.Validator;

import javax.inject.Inject;

import static com.malec.netCrackerLab.util.Ext.append;
import static com.malec.netCrackerLab.util.Ext.newLine;

public class ContractParser {
    @Inject
    public Logger logger;
    @Inject
    public Validator<Contract> validator;
    @Inject
    private Reader reader;
    @Inject
    private CSVParser parser;

    public ContractParser() {
        Injector.inject(this);
    }

    public ContractAdapter parse() {
        ContractAdapter adapter = new ContractAdapter();

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
                    if (!result.isValid()) {
                        errorCount++;
                        if (logger != null)
                            logger.error(result.getMessage());
                    } else if (logger != null)
                        logger.note(result.getMessage());
                }
                logResult(logger, errorCount, contract.toString());
                if (errorCount == 0)
                    adapter.add(contract);
            } else
                adapter.add(contract);
        }

        return adapter;
    }

    private void logResult(Logger logger, int errorCount, String currentContract) {
        if (logger == null)
            return;

        StringBuilder sb = new StringBuilder();
        append(sb, "Validation finished with ", errorCount, " error");
        if (errorCount != 1)
            append(sb, "s");
        if (errorCount > 0)
            append(sb, newLine(), "Current contract [", currentContract, "] will be ignored", newLine());
        else
            append(sb, newLine());
        logger.note(sb.toString());
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
