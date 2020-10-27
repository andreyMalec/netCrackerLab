package com.malec.netCrackerLab.model;

import com.malec.netCrackerLab.util.DateConverter;

public class Client {
    Integer id;
    String fullName;
    Long birthday;
    Gender sex;
    Integer passportSeries, passportNumber;

    public Client(Integer id, String fullName, Long birthday, Gender sex, Integer passportSeries, Integer passportNumber) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.sex = sex;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public int getAge() {
        return DateConverter.getAge(birthday);
    }
}