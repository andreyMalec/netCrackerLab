package com.malec.netCrackerLab.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayAdapter<T> {
    protected static final int EXTENSION_SIZE = 10;

    protected static final AdapterFilter filter = new AdapterFilter() {};

    protected static final AdapterSorter sorter = new QuickSorter();
    protected static final AdapterSorter bubbleSorter = new BubbleSorter();

    protected int size;
    protected Object[] data;

    public ArrayAdapter() {
        this.size = 0;
        this.data = new Object[size];
    }

    public ArrayAdapter(ArrayAdapter<T> anotherAdapter) {
        this.size = anotherAdapter.size;
        this.data = anotherAdapter.data.clone();
    }

    public int getSize() {
        return size;
    }

    public void add(T element) {
        if (isFull())
            expand();

        data[size++] = element;
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

    public T removeAt(int index) {
        checkBounds(index);

        size--;
        T element = (T) data[index];
        decSizeBetween(index);

        return element;
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++)
            if (data[i].equals(element)) {
                return i;
            }

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
        return filter.filter(this, 0, size - 1, predicate);
    }

    public ArrayAdapter<T> sort(AdapterSorter sorter, Comparator<? super T> comparator) {
        return sorter.sort(this, 0, size - 1, comparator);
    }

    public ArrayAdapter<T> sort(Comparator<? super T> comparator) {
        return sort(sorter, comparator);
    }

    public ArrayAdapter<T> bubbleSort(Comparator<? super T> comparator) {
        return sort(bubbleSorter, comparator);
    }

    public <E> ArrayAdapter<E> map(Function<? super T, E> mapper) {
        ArrayAdapter<E> mapped = new ArrayAdapter<>();

        for (int i = 0; i < size; i++)
            mapped.add(mapper.apply((T) data[i]));

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

    protected void incSizeBetween(int index) {
        expand(1);
        System.arraycopy(data, index, data, index + 1, size - index);
    }

    protected void decSizeBetween(int index) {
        System.arraycopy(data, index + 1, data, index, size - index);
    }

    protected void expand() {
        expand(EXTENSION_SIZE);
    }

    protected void expand(int extensionSize) {
        Object[] tmp = data.clone();
        data = new Object[tmp.length + extensionSize];
        System.arraycopy(tmp, 0, data, 0, size);
    }

    protected void checkBounds(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    protected boolean isFull() {
        return size >= data.length;
    }

    protected abstract static class AdapterFilter {
        <T> ArrayAdapter<T> filter(ArrayAdapter<T> adapter, int startIndex, int endIndex, Predicate<? super T> predicate) {
            ArrayAdapter<T> filtered = new ArrayAdapter<>();

            for (int i = startIndex; i < endIndex + 1; i++) {
                T element = adapter.getByIndex(i);
                if (predicate.test(element))
                    filtered.add(element);
            }

            return filtered;
        }
    }
}