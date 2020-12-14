package com.malec.netCrackerLab.model;

public class InternetContract extends Contract {
    private final Integer speedLimit;

    public InternetContract(Integer speedLimit, Integer id, Long startDate, Long endDate, Client client) {
        super(id, startDate, endDate, client);

        this.speedLimit = speedLimit;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }
}