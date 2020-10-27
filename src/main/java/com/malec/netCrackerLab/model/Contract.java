package com.malec.netCrackerLab.model;

public abstract class Contract {
    private Integer id;
    private Long startDate, endDate;
    private Client client;

    public Contract(Integer id, Long startDate, Long endDate, Client client) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }
}
