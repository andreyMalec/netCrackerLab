package com.malec.netCrackerLab;

import com.malec.netCrackerLab.util.ArrayComparator;
import com.malec.netCrackerLab.util.ArraySorter;

public class ArrayAdapter<T> {
    protected static final int EXTENSION_SIZE = 10;

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
        int index = -1;
        for (int i = 0; i < size; i++)
            if (data[i].equals(element)) {
                index = i;
                break;
            }

        return index;
    }

    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    public T getByIndex(int index) {
        checkBounds(index);

        return (T) data[index];
    }

    public void sort(ArraySorter sorter, ArrayComparator<? super T> comparator) {
        sorter.sort((T[]) data, 0, size - 1, comparator);
    }

    @Override
    public ArrayAdapter<T> clone() {
        try {
            super.clone();
        } catch (Exception ignored) { }

        return new ArrayAdapter<T>(this);
    }

    protected void incSizeBetween(int index) {
        int count = size - index - 1;
        Object[] tmp = new Object[size];
        System.arraycopy(data, 0, tmp, 0, index);
        System.arraycopy(data, index, tmp, index + 1, count);
        data = tmp;
    }

    protected void decSizeBetween(int index) {
        int count = size - index;
        Object[] tmp = new Object[size];
        System.arraycopy(data, 0, tmp, 0, index);
        System.arraycopy(data, index + 1, tmp, index, count);
        data = tmp;
    }

    protected void expand() {
        Object[] tmp = data.clone();
        data = new Object[tmp.length + EXTENSION_SIZE];
        System.arraycopy(tmp, 0, data, 0, size);
    }

    protected void checkBounds(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    protected boolean isFull() {
        return size >= data.length;
    }
}