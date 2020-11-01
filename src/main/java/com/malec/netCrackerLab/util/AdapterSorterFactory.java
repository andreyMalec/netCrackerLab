package com.malec.netCrackerLab.util;

public class AdapterSorterFactory {
    public static AdapterSorter getSorter() {
        return new QuickSorter();
    }

    public static AdapterSorter getBubbleSorter() {
        return new BubbleSorter();
    }
}
