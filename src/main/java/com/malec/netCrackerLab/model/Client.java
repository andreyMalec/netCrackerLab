package com.malec.netCrackerLab.model;

import com.malec.netCrackerLab.util.DateConverter;

public class Client {
    private Integer id;
    private String fullName;
    private Long birthday;
    private Gender sex;
    private Integer passportSeries, passportNumber;

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

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getBirthday() {
        return birthday;
    }

    public Gender getSex() {
        return sex;
    }

    public Integer getPassportSeries() {
        return passportSeries;
    }

    public Integer getPassportNumber() {
        return passportNumber;
    }
}