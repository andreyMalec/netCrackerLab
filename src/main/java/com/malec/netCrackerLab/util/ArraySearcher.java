package com.malec.netCrackerLab.util;

import java.util.function.Function;

public interface ArraySearcher {
    <T> T search(T[] array, int startIndex, int endIndex, Function<? super T, Boolean> equals);
}
