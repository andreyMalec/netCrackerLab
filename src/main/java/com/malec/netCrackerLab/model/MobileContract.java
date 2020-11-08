package com.malec.netCrackerLab.model;

public class MobileContract extends Contract {
    private Integer minutes, sms, gb;
    private Contract contract;

    public MobileContract(Integer minutes, Integer sms, Integer gb, Integer id, Long startDate, Long endDate, Client client) {
        super(id, startDate, endDate, client);

        this.minutes = minutes;
        this.sms = sms;
        this.gb = gb;
    }

    public MobileContract(Integer minutes, Integer sms, Integer gb, Contract contract) {
        super(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                contract.getClient()
        );

        this.minutes = minutes;
        this.sms = sms;
        this.gb = gb;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Integer getSms() {
        return sms;
    }

    public Integer getGb() {
        return gb;
    }
}