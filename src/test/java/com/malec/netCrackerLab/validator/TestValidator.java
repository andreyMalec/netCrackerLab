package com.malec.netCrackerLab.validator;

import com.malec.netCrackerLab.model.Client;
import com.malec.netCrackerLab.model.Gender;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidator {
    @Test
    public void test() {
        ValidatorBuilder<Client> builder = new ValidatorBuilder<>();

        Client client = new Client(0, "name", 2000L, Gender.FEMALE, 123, 456789);

        builder.add(new Condition<>("surname", Client::getFullName));
        builder.add(new Condition<>(125,
                Conditions.GREATER_THAN,
                Client::getPassportSeries,
                (expected, actual) -> expected > actual
        ));
        builder.add(new Condition<>(2048L,
                Conditions.LESS_THAN_OR_EQUALS,
                Client::getBirthday,
                (expected, actual) -> expected <= actual
        ));

        Validator<Client> v = builder.build();
        List<ValidationResult> results = v.validate(client);

        assertFalse(results.get(0).isValid());
        assertTrue(results.get(1).isValid());
        assertFalse(results.get(2).isValid());
    }
}
