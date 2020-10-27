package com.malec.netCrackerLab.util;

import java.util.function.BiFunction;

public class QuickSorter implements ArraySorter {
    @Override
    public <T> void sort(T[] array, int startIndex, int endIndex, BiFunction<? super T, ? super T, Integer> comparator) {
        if (startIndex < endIndex) {
            int partitionIndex = partition(array, startIndex, endIndex, comparator);

            sort(array, startIndex, partitionIndex - 1, comparator);
            sort(array, partitionIndex + 1, endIndex, comparator);
        }
    }

    private <T> int partition(T[] array, int startIndex, int endIndex, BiFunction<? super T, ? super T, Integer> comparator) {
        int i = (startIndex - 1);

        for (int j = startIndex; j < endIndex; j++)
            if (comparator.apply(array[j], array[endIndex]) < 0)
                swap(array, ++i, j);

        swap(array, ++i, endIndex);

        return i;
    }
}