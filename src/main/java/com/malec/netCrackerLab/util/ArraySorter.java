package com.malec.netCrackerLab.util;

import java.util.function.BiFunction;

public interface ArraySorter {
    <T> void sort(T[] array, int startIndex, int endIndex, BiFunction<? super T, ? super T, Integer> comparator);

    default <T> void swap(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
