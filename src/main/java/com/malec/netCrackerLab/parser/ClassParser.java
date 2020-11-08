package com.malec.netCrackerLab.parser;

public interface ClassParser {
    <T> T from(String source, Class<T> jClass);

    <T> String to(T object);
}
