package com.malec.netCrackerLab.model;

public class TVContract extends Contract {
    private TVPackage tvPackage;

    public TVContract(Integer id, Long startDate, Long endDate, Client client, TVPackage tvPackage) {
        super(id, startDate, endDate, client);

        this.tvPackage = tvPackage;
    }
}