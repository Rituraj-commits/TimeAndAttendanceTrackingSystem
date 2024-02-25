package com.adp.timeattendance.model;
import java.sql.Date;

public class MonthDate {
    private String name;
    private Integer specifier;
    private Integer numberOfDays;
    private String startDate;
    private String endDate;

    private String year;

    public String getName() {
        return name;
    }

    public String getYear(){
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setYear(String year) {this.year = year;}
    public Integer getSpecifier() {
        return specifier;
    }

    public void setSpecifier(Integer specifier) {
        this.specifier = specifier;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public MonthDate() {
    }

    public MonthDate(String name, Integer specifier, Integer numberOfDays, String startDate, String endDate,String year) {
        this.name = name;
        this.specifier = specifier;
        this.numberOfDays = numberOfDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
    }
}
