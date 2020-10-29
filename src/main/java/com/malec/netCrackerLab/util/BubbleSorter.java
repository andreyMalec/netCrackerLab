package com.malec.netCrackerLab.util;

import java.util.Comparator;

public class BubbleSorter implements AdapterSorter {
    @Override
    public <T> ArrayAdapter<T> sort(ArrayAdapter<T> adapter, int startIndex, int endIndex, Comparator<? super T> comparator) {
        ArrayAdapter<T> sorted = adapter.clone();

        for (int i = startIndex; i < endIndex + 1; i++)
            for (int j = startIndex + 1; j < (endIndex - i + 1); j++)
                if (comparator.compare(sorted.getByIndex(j - 1), sorted.getByIndex(j)) > 0)
                    swap(sorted, j - 1, j);

        return sorted;
    }
}
