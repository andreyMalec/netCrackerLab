package com.malec.netCrackerLab.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorBuilder<T> {
    private List<Condition<T>> conditions = new ArrayList<>();

    public ValidatorBuilder<T> add(Condition<T> condition) {
        conditions.add(condition);
        return this;
    }

    public Validator<T> build() {
        return new Validator<T>(conditions);
    }
}
