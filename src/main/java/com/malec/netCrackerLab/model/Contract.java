package com.malec.netCrackerLab.model;

public class Contract {
    private final Integer id;
    private final Long startDate, endDate;
    private final Client client;

    public Contract(Integer id, Long startDate, Long endDate, Client client) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Contract{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", client=" +
                client + '}';
    }
}
