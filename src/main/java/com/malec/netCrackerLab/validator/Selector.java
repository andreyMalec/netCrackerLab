package com.malec.netCrackerLab.validator;

@FunctionalInterface
public interface Selector<T, E> {
    T select(E object);
}
