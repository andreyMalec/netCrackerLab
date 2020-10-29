package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.util.AdapterSorter;
import com.malec.netCrackerLab.util.ArrayAdapter;

import java.util.Comparator;
import java.util.function.Predicate;

public class ContractAdapter extends ArrayAdapter<Contract> {
    public ContractAdapter() {
        super();
    }

    public ContractAdapter(ContractAdapter anotherAdapter) {
        super(anotherAdapter);
    }

    private ContractAdapter(ArrayAdapter<Contract> superAdapter) {
        super(superAdapter);
    }

    /**
     * Adds a new contract to the end of the list
     *
     * @param element new contract
     */
    @Override
    public void add(Contract element) {
        super.add(element);
    }

    /**
     * @param id id of the required contract
     * @return required contract
     */
    public Contract getById(Integer id) {
        int index = indexById(id);

        if (index >= 0)
            return (Contract) data[index];
        else
            return null;
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
     * Adds a new contract in the specified position
     *
     * @param element new contract
     * @param index   position in the list
     * @throws IndexOutOfBoundsException index
     */
    @Override
    public void insert(Contract element, int index) {
        super.insert(element, index);
    }

    /**
     * Deletes the contract for the specified position
     *
     * @param index position in the list
     * @return deleted element
     * @throws IndexOutOfBoundsException index
     */
    @Override
    public Contract removeAt(int index) {
        return super.removeAt(index);
    }

    /**
     * @param id id of the required contract
     * @return index of the required contract in the list
     */
    public int indexById(Integer id) {
        for (int i = 0; i < size; i++)
            if (((Contract) data[i]).getId().equals(id))
                return i;

        return -1;
    }

    /**
     * @param contract required contract
     * @return index of the required contract in the list
     */
    @Override
    public int indexOf(Contract contract) {
        return super.indexOf(contract);
    }

    /**
     * @param contract required contract
     * @return true if required contract contains in the list
     */
    @Override
    public boolean contains(Contract contract) {
        return super.contains(contract);
    }

    /**
     * Returns a {@link ContractAdapter} consisting of the elements of this adapter, sorted according to the provided {@link Comparator}
     *
     * @param sorter     implementation of the {@link AdapterSorter} class that defines the sorting algorithm
     * @param comparator a comparator to be used to compare adapter elements
     * @return the new ContractAdapter
     */
    @Override
    public ContractAdapter sort(AdapterSorter sorter, Comparator<? super Contract> comparator) {
        return new ContractAdapter(super.sort(sorter, comparator));
    }

    /**
     * Returns a {@link ContractAdapter} consisting of the elements of this adapter, sorted according to the provided {@link Comparator} with QuickSort algorithm
     *
     * @param comparator a comparator to be used to compare adapter elements
     * @return the new ContractAdapter
     */
    @Override
    public ContractAdapter sort(Comparator<? super Contract> comparator) {
        return new ContractAdapter(super.sort(comparator));
    }

    /**
     * Returns a {@link ContractAdapter} consisting of the elements of this adapter, sorted according to the provided {@link Comparator} with BubbleSort algorithm
     *
     * @param comparator a comparator to be used to compare adapter elements
     * @return the new ContractAdapter
     */
    @Override
    public ContractAdapter bubbleSort(Comparator<? super Contract> comparator) {
        return new ContractAdapter(super.bubbleSort(comparator));
    }

    /**
     * Returns a {@link ContractAdapter} consisting of the elements of this adapter that match the given {@link Predicate}.
     *
     * @param predicate a predicate to apply to each element to determine if it should be included
     * @return the new ContractAdapter
     */
    @Override
    public ContractAdapter filter(Predicate<? super Contract> predicate) {
        return new ContractAdapter(super.filter(predicate));
    }

    /**
     * @param index position in the list
     * @return required contract
     * @throws IndexOutOfBoundsException index
     */
    public Contract getByIndex(int index) {
        return super.getByIndex(index);
    }
}
