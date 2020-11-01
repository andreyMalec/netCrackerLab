package com.malec.netCrackerLab.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayAdapter<T> {
    protected static final int EXTENSION_SIZE = 10;

    protected AdapterFilter filter;
    protected AdapterSorter sorter, bubbleSorter;

    protected int size;
    protected Object[] data;

    public ArrayAdapter() {
        this.size = 0;
        this.data = new Object[size];
    }

    public ArrayAdapter(ArrayAdapter<T> anotherAdapter) {
        this.size = anotherAdapter.size;
        this.data = anotherAdapter.data.clone();
        this.filter = anotherAdapter.filter;
        this.sorter = anotherAdapter.sorter;
        this.bubbleSorter = anotherAdapter.bubbleSorter;
    }

    public int getSize() {
        return size;
    }

    public void add(T element) {
        if (isFull())
            expand();

        data[size++] = element;
    }

    protected boolean isFull() {
        return size >= data.length;
    }

    protected void expand() {
        expand(EXTENSION_SIZE);
    }

    protected void expand(int extensionSize) {
        Object[] tmp = data.clone();
        data = new Object[tmp.length + extensionSize];
        System.arraycopy(tmp, 0, data, 0, size);
    }

    public void insert(T element, int index) {
        if (index == size) {
            add(element);
            return;
        }

        checkBounds(index);

        size++;
        incSizeBetween(index);
        data[index] = element;
    }

    protected void checkBounds(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    protected void incSizeBetween(int index) {
        expand(1);
        System.arraycopy(data, index, data, index + 1, size - index);
    }

    public T removeAt(int index) {
        checkBounds(index);

        size--;
        Object element = data[index];
        decSizeBetween(index);

        return (T) element;
    }

    protected void decSizeBetween(int index) {
        System.arraycopy(data, index + 1, data, index, size - index);
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++)
            if (data[i].equals(element))
                return i;

        return -1;
    }

    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    public T getByIndex(int index) {
        checkBounds(index);

        return (T) data[index];
    }

    public ArrayAdapter<T> filter(Predicate<? super T> predicate) {
        initFilter();
        return filter.filter(this, 0, size, predicate);
    }

    protected void initFilter() {
        if (filter == null)
            filter = AdapterFilter.instance();
    }

    public ArrayAdapter<T> sorted(AdapterSorter sorter, Comparator<? super T> comparator) {
        return sorter.sorted(this, 0, size, comparator);
    }

    public ArrayAdapter<T> sorted(Comparator<? super T> comparator) {
        initSorter();
        return sorted(sorter, comparator);
    }

    protected void initSorter() {
        if (sorter == null)
            sorter = AdapterSorterFactory.getSorter();
    }

    public ArrayAdapter<T> bubbleSort(Comparator<? super T> comparator) {
        initBubbleSorter();
        return sorted(bubbleSorter, comparator);
    }

    protected void initBubbleSorter() {
        if (bubbleSorter == null)
            bubbleSorter = AdapterSorterFactory.getBubbleSorter();
    }

    public <E> ArrayAdapter<E> map(Function<? super T, E> mapper) {
        ArrayAdapter<E> mapped = new ArrayAdapter<>();

        for (int i = 0; i < size; i++)
            mapped.add(mapper.apply(getByIndex(i)));

        return mapped;
    }

    public void swap(int firstIndex, int secondIndex) {
        checkBounds(firstIndex);
        checkBounds(secondIndex);

        Object temp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temp;
    }

    @Override
    public ArrayAdapter<T> clone() {
        try {
            super.clone();
        } catch (Exception ignored) { }

        return new ArrayAdapter<>(this);
    }

    protected abstract static class AdapterFilter {
        public static AdapterFilter instance() {
            return new AdapterFilter() {};
        }

        <T> ArrayAdapter<T> filter(ArrayAdapter<T> adapter, int startIndex, int count, Predicate<? super T> predicate) {
            ArrayAdapter<T> filtered = new ArrayAdapter<>();

            for (int i = startIndex; i < count; i++) {
                T element = adapter.getByIndex(i);
                if (predicate.test(element))
                    filtered.add(element);
            }

            return filtered;
        }
    }
}