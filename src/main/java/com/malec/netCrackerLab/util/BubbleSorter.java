package com.malec.netCrackerLab.util;

import java.util.Comparator;

public class BubbleSorter implements AdapterSorter {
    @Override
    public <T> ArrayAdapter<T> sorted(ArrayAdapter<T> adapter, int startIndex, int count, Comparator<? super T> comparator) {
        ArrayAdapter<T> sorted = adapter.clone();

        for (int i = startIndex; i < count; i++)
            for (int j = startIndex + 1; j < (count - i); j++)
                if (comparator.compare(sorted.getByIndex(j - 1), sorted.getByIndex(j)) > 0)
                    swap(sorted, j - 1, j);

        return sorted;
    }
}
