package com.malec.netCrackerLab.util;

import java.util.function.Function;

public class LinearSearcher implements ArraySearcher {
    @Override
    public <T> T search(T[] array, int startIndex, int endIndex, Function<? super T, Boolean> predicate) {
        for (int i = startIndex; i < endIndex + 1; i++)
            if (predicate.apply(array[i]))
                return array[i];

        return null;
    }
}
