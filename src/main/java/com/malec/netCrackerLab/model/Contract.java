package com.malec.netCrackerLab.model;

public abstract class Contract {
    public Integer id;
    public Long startDate, endDate;
    public Client client;

    public Contract(Integer id, Long startDate, Long endDate, Client client) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }
}
