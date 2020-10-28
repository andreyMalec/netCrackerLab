package com.malec.netCrackerLab.util;

import java.util.function.ToIntBiFunction;

public class QuickSorter implements AdapterSorter {
    @Override
    public <T> ArrayAdapter<T> sort(ArrayAdapter<T> adapter, int startIndex, int endIndex, ToIntBiFunction<? super T, ? super T> comparator) {
        ArrayAdapter<T> sorted = adapter.clone();

        quickSort(sorted, startIndex, endIndex, comparator);

        return sorted;
    }

    private <T> void quickSort(ArrayAdapter<T> adapter, int startIndex, int endIndex, ToIntBiFunction<? super T, ? super T> comparator) {
        if (startIndex < endIndex) {
            int partitionIndex = partition(adapter, startIndex, endIndex, comparator);

            quickSort(adapter, startIndex, partitionIndex - 1, comparator);
            quickSort(adapter, partitionIndex + 1, endIndex, comparator);
        }
    }

    private <T> int partition(ArrayAdapter<T> adapter, int startIndex, int endIndex, ToIntBiFunction<? super T, ? super T> comparator) {
        int i = (startIndex - 1);

        for (int j = startIndex; j < endIndex; j++)
            if (comparator.applyAsInt(adapter.getByIndex(j), adapter.getByIndex(endIndex)) < 0)
                swap(adapter, ++i, j);

        swap(adapter, ++i, endIndex);

        return i;
    }
}