package com.malec.netCrackerLab.validator;

@FunctionalInterface
public interface Verifier<T> {
    boolean verify(T expected, T actual);
}
