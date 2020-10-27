package com.malec.netCrackerLab.util;

public interface ArraySorter {
    <T> void sort(T[] array, int startIndex, int endIndex, ArrayComparator<? super T> comparator);

    default <T> void swap(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
