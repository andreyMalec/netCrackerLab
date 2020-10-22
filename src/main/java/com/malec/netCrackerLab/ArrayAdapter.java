package com.malec.netCrackerLab;

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
        if (isNotFull()) {
            data[size] = element;
            size++;
        } else {
            expand();
            add(element);
        }
    }

    public void insert(T element, int index) {
        if (size == 0 || index == size) {
            add(element);
            return;
        }

        checkBounds(index);

        size++;
        Object[] tmp = incSizeBetween(index);
        tmp[index] = element;
        data = tmp;
    }

    public T removeAt(int index) {
        checkBounds(index);

        size--;
        T element = (T) data[index];
        data = decSizeBetween(index);

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

    @Override
    public ArrayAdapter<T> clone() {
        try {
            super.clone();
        } catch (Exception ignored) { }

        return new ArrayAdapter<T>(this);
    }

    protected Object[] incSizeBetween(int index) {
        int count = size - index - 1;
        Object[] tmp = new Object[size];
        System.arraycopy(data, 0, tmp, 0, index);
        System.arraycopy(data, index, tmp, index + 1, count);
        return tmp;
    }

    protected Object[] decSizeBetween(int index) {
        int count = size - index;
        Object[] tmp = new Object[size];
        System.arraycopy(data, 0, tmp, 0, index);
        System.arraycopy(data, index + 1, tmp, index, count);
        return tmp;
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

    protected boolean isNotFull() {
        return size < data.length;
    }
}