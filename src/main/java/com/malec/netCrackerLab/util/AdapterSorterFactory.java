package com.malec.netCrackerLab.util;

public class AdapterSorterFactory {
    protected static AdapterSorter sorter, bubbleSorter;

    private AdapterSorterFactory() {}

    public static AdapterSorter getSorter() {
        if (sorter == null)
            sorter = new QuickSorter();

        return sorter;
    }

    public static AdapterSorter getBubbleSorter() {
        if (bubbleSorter == null)
            bubbleSorter = new BubbleSorter();

        return bubbleSorter;
    }
}
