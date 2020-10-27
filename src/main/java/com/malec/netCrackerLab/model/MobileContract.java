package com.malec.netCrackerLab.model;

public class MobileContract extends Contract {
    private Integer minutes, sms, gb;

    public MobileContract(Integer id, Long startDate, Long endDate, Client client, Integer minutes, Integer sms, Integer gb) {
        super(id, startDate, endDate, client);

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