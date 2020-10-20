package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Contract;

public class ContractAdapter {
    private static final int EXTENSION_SIZE = 10;

    private int size;
    private Contract[] contracts;

    public ContractAdapter() {
        this.size = 0;
        this.contracts = new Contract[size];
    }

    public ContractAdapter(ContractAdapter anotherAdapter) {
        this.size = anotherAdapter.size;
        this.contracts = anotherAdapter.contracts.clone();
    }

    public int getSize() {
        return size;
    }

    /**
     * Adds a new contract to the end of the list
     *
     * @param element new contract
     */
    public void add(Contract element) {
        if (isNotFull()) {
            contracts[size] = element;
            size++;
        } else {
            expand();
            add(element);
        }
    }

    /**
     * Adds a new contract in the specified position
     *
     * @param element new contract
     * @param index   position in the list
     * @throws IndexOutOfBoundsException index
     */
    public void insert(Contract element, int index) {
        if (size == 0 || index == size) {
            add(element);
            return;
        }

        checkBounds(index);

        size++;
        Contract[] tmp = incSizeBetween(index);
        tmp[index] = element;
        contracts = tmp;
    }

    /**
     * Deletes the contract for the specified position
     *
     * @param index position in the list
     * @return deleted element
     * @throws IndexOutOfBoundsException index
     */
    public Contract removeAt(int index) {
        checkBounds(index);

        size--;
        Contract element = contracts[index];
        contracts = decSizeBetween(index);

        return element;
    }

    /**
     * Deletes the contract for the specified id
     *
     * @param id id of the required contract
     * @return deleted element
     */
    public Contract removeById(Integer id) {
        int index = indexById(id);

        if (index >= 0)
            return removeAt(index);
        else
            return null;
    }

    /**
     * @param id id of the required contract
     * @return index of the required contract in the list
     */
    public int indexById(Integer id) {
        int index = -1;
        for (int i = 0; i < size; i++)
            if (contracts[i].id.equals(id)) {
                index = i;
                break;
            }

        return index;
    }

    /**
     * @param contract required contract
     * @return index of the required contract in the list
     */
    public int indexOf(Contract contract) {
        return indexById(contract.id);
    }

    /**
     * @param contract required contract
     * @return true if required contract contains in the list
     */
    public boolean contains(Contract contract) {
        return indexOf(contract) != -1;
    }

    /**
     * @param id position in the list
     * @return required contract
     */
    public Contract getById(Integer id) {
        int index = indexById(id);

        if (index >= 0)
            return contracts[index];
        else
            return null;
    }

    /**
     * @param index id of the required contract
     * @return required contract
     * @throws IndexOutOfBoundsException index
     */
    public Contract getByIndex(int index) {
        checkBounds(index);

        return contracts[index];
    }

    @Override
    public ContractAdapter clone() {
        try {
            super.clone();
        } catch (Exception ignored) { }

        return new ContractAdapter(this);
    }

    private Contract[] incSizeBetween(int index) {
        int count = size - index - 1;
        Contract[] tmp = new Contract[size];
        System.arraycopy(contracts, 0, tmp, 0, index);
        System.arraycopy(contracts, index, tmp, index + 1, count);
        return tmp;
    }

    private Contract[] decSizeBetween(int index) {
        int count = size - index;
        Contract[] tmp = new Contract[size];
        System.arraycopy(contracts, 0, tmp, 0, index);
        System.arraycopy(contracts, index + 1, tmp, index, count);
        return tmp;
    }

    private void expand() {
        Contract[] tmp = contracts.clone();
        contracts = new Contract[tmp.length + EXTENSION_SIZE];
        System.arraycopy(tmp, 0, contracts, 0, size);
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private boolean isNotFull() {
        return size < contracts.length;
    }
}
