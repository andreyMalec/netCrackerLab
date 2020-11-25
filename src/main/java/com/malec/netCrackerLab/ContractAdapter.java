package com.malec.netCrackerLab;

import com.malec.netCrackerLab.di.Injector;
import com.malec.netCrackerLab.model.Contract;
import com.malec.netCrackerLab.util.AdapterSorter;
import com.malec.netCrackerLab.util.ArrayAdapter;

import java.util.Comparator;
import java.util.function.Predicate;

import javax.inject.Inject;

public class ContractAdapter extends ArrayAdapter<Contract> {
    @Inject
    protected AdapterSorter sorter;

    public ContractAdapter() {
        super();
        Injector.inject(this);
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
     * @param id id of the required contract
     * @return index of the required contract in the list
     */
    protected int indexById(Integer id) {
        for (int i = 0; i < size; i++)
            if (getByIndex(i).getId().equals(id))
                return i;

        return -1;
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
     * Returns a {@link ContractAdapter} consisting of the elements of this adapter, sorted according to the provided {@link Comparator}
     *
     * @param sorter     implementation of the {@link AdapterSorter} class that defines the sorting algorithm
     * @param comparator a comparator to be used to compare adapter elements
     * @return the new ContractAdapter
     */
    @Override
    public ContractAdapter sorted(AdapterSorter sorter, Comparator<? super Contract> comparator) {
        return new ContractAdapter(super.sorted(sorter, comparator));
    }

    public ContractAdapter sorted(Comparator<? super Contract> comparator) {
        return sorted(sorter, comparator);
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

    @Override
    public ContractAdapter clone() {
        try {
            super.clone();
        } catch (Exception ignored) { }

        return new ContractAdapter(this);
    }
}
