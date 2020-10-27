package com.malec.netCrackerLab.model;

public class InternetContract extends Contract {
    private Integer speedLimit;

    public InternetContract(Integer id, Long startDate, Long endDate, Client client, Integer speedLimit) {
        super(id, startDate, endDate, client);

        this.speedLimit = speedLimit;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }
}