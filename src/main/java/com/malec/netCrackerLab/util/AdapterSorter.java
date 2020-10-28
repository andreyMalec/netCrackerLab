package com.malec.netCrackerLab.util;

import java.util.function.ToIntBiFunction;

public interface AdapterSorter {
    <T> ArrayAdapter<T> sort(ArrayAdapter<T> adapter, int startIndex, int endIndex, ToIntBiFunction<? super T, ? super T> comparator);

    default <T> void swap(ArrayAdapter<T> adapter, int firstIndex, int secondIndex) {
        adapter.swap(firstIndex, secondIndex);
    }
}
