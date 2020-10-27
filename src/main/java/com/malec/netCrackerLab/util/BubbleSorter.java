package com.malec.netCrackerLab.util;

import java.util.function.BiFunction;

public class BubbleSorter implements ArraySorter {
    @Override
    public <T> void sort(T[] array, int startIndex, int endIndex, BiFunction<? super T, ? super T, Integer> comparator) {
        for (int i = startIndex; i < endIndex + 1; i++)
            for (int j = startIndex + 1; j < (endIndex - i + 1); j++)
                if (comparator.apply(array[j - 1], array[j]) > 0)
                    swap(array, j - 1, j);
    }
}
