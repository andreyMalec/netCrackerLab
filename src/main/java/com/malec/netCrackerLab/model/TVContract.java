package com.malec.netCrackerLab.model;

public class TVContract extends Contract {
    private final TVPackage tvPackage;

    public TVContract(TVPackage tvPackage, Integer id, Long startDate, Long endDate, Client client) {
        super(id, startDate, endDate, client);

        this.tvPackage = tvPackage;
    }

    public TVPackage getTvPackage() {
        return tvPackage;
    }
}