package com.malec.netCrackerLab.model;

public class InternetContract extends Contract {
    private Integer speedLimit;
    private Contract contract;

    public InternetContract(Integer speedLimit, Integer id, Long startDate, Long endDate, Client client) {
        super(id, startDate, endDate, client);

        this.speedLimit = speedLimit;
    }

    public InternetContract(Integer speedLimit, Contract contract) {
        super(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                contract.getClient()
        );

        this.speedLimit = speedLimit;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }
}