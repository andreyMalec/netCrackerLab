package com.malec.netCrackerLab.model;

public class TVContract extends Contract {
    private TVPackage tvPackage;
    private Contract contract;

    public TVContract(TVPackage tvPackage, Integer id, Long startDate, Long endDate, Client client) {
        super(id, startDate, endDate, client);

        this.tvPackage = tvPackage;
    }

    public TVContract(TVPackage tvPackage, Contract contract) {
        super(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                contract.getClient()
        );

        this.tvPackage = tvPackage;
    }

    public TVPackage getTvPackage() {
        return tvPackage;
    }
}