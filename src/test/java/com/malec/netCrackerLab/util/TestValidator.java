package com.malec.netCrackerLab.util;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Gender;
import com.malec.netCrackerLab.validator.Condition;
import com.malec.netCrackerLab.validator.Conditions;
import com.malec.netCrackerLab.validator.ValidationResult;
import com.malec.netCrackerLab.validator.Validator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidator {
    @Test
    public void test() {
        List<Condition<Client>> conditions = new ArrayList<>();

        Client client = new Client(0, "name", 2000L, Gender.FEMALE, 123, 456789);

        conditions.add(new Condition<>("surname", Client::getFullName, String::equals));
        conditions.add(new Condition<>(125, Conditions.GREATER_THAN, Client::getPassportSeries,
                (expected, actual) -> expected > actual
        ));
        conditions.add(new Condition<>(2048L, Conditions.LESS_THAN_OR_EQUALS, Client::getBirthday,
                (expected, actual) -> expected <= actual
        ));

        Validator<Client> v = new Validator<>(conditions);
        List<ValidationResult> results = v.validate(client);

        assertFalse(results.get(0).isValid());
        assertTrue(results.get(1).isValid());
        assertFalse(results.get(2).isValid());
    }
}
