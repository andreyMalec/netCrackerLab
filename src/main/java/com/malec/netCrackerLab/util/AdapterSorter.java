package com.malec.netCrackerLab.util;

import java.util.Comparator;

public interface AdapterSorter {
    <T> ArrayAdapter<T> sorted(
            ArrayAdapter<T> adapter, int startIndex, int count, Comparator<? super T> comparator
    );

    default <T> void swap(ArrayAdapter<T> adapter, int firstIndex, int secondIndex) {
        adapter.swap(firstIndex, secondIndex);
    }
}
