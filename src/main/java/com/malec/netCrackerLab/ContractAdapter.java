package com.malec.netCrackerLab;

import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.util.AdapterSorter;
import com.malec.netCrackerLab.util.ArrayAdapter;

import java.util.function.Function;
import java.util.function.ToIntBiFunction;

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
     * Sorts the data according to the order induced by the specified comparator
     *
     * @param sorter     implementation of the {@link AdapterSorter} class that defines the sorting algorithm
     * @param comparator lambda that specified sorting method. Compare two contracts and returns the value zero if (x == y),
     *                   if (x < y) then it returns a value less than zero and
     *                   if (x > y) then it returns a value greater than zero
     */
    @Override
    public ContractAdapter sort(AdapterSorter sorter, ToIntBiFunction<? super Contract, ? super Contract> comparator) {
        return new ContractAdapter(super.sort(sorter, comparator));
    }

    /**
     * Sorts the data according to the order induced by the specified comparator with QuickSort algorithm
     *
     * @param comparator lambda that specified sorting method. Compare two contracts and returns the value zero if (x == y),
     *                   if (x < y) then it returns a value less than zero and
     *                   if (x > y) then it returns a value greater than zero
     */
    @Override
    public ContractAdapter sort(ToIntBiFunction<? super Contract, ? super Contract> comparator) {
        return new ContractAdapter(super.sort(comparator));
    }

    /**
     * Sorts the data according to the order induced by the specified comparator with BubbleSort algorithm
     *
     * @param comparator lambda that specified sorting method. Compare two contracts and returns the value zero if (x == y),
     *                   if (x < y) then it returns a value less than zero and
     *                   if (x > y) then it returns a value greater than zero
     */
    @Override
    public ContractAdapter bubbleSort(ToIntBiFunction<? super Contract, ? super Contract> comparator) {
        return new ContractAdapter(super.bubbleSort(comparator));
    }

    /**
     * Search the data by the specified predicate
     *
     * @param predicate lambda that specified searching method. Compare contract and return true if it equal to your specified value else false
     * @return required contract
     */
    @Override
    public ContractAdapter filter(Function<? super Contract, Boolean> predicate) {
        return new ContractAdapter(super.filter(predicate));
    }

    /**
     * @param id position in the list
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
     * @param index id of the required contract
     * @return required contract
     * @throws IndexOutOfBoundsException index
     */
    public Contract getByIndex(int index) {
        return super.getByIndex(index);
    }
}
