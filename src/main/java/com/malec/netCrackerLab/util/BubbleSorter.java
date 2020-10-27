package com.malec.netCrackerLab.util;


public class BubbleSorter implements ArraySorter {
    @Override
    public <T> void sort(T[] array, int startIndex, int endIndex, ArrayComparator<? super T> comparator) {
        for (int i = startIndex; i < endIndex + 1; i++)
            for (int j = startIndex + 1; j < (endIndex - i + 1); j++)
                if (comparator.compare(array[j - 1], array[j]) > 0)
                    swap(array, j - 1, j);
    }
}
