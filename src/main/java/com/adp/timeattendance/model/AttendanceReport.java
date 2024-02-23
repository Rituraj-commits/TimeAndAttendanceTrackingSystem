package com.adp.timeattendance.model;

import java.math.BigDecimal;


public class AttendanceReport {

    private Integer id;

    private String name;

    private BigDecimal lateArrivals;

    private BigDecimal totalOvertimeHours;


    private BigDecimal totalPresents;

    public AttendanceReport() {
        super();
    }

    public AttendanceReport(Integer id, String name, BigDecimal lateArrivals, BigDecimal totalOvertimeHours,
                            BigDecimal totalPresents) {
        super();
        this.id = id;
        this.name = name;
        this.lateArrivals = lateArrivals;
        this.totalOvertimeHours = totalOvertimeHours;

        this.totalPresents = totalPresents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLateArrivals() {
        return lateArrivals;
    }

    public void setLateArrivals(BigDecimal lateArrivals) {
        this.lateArrivals = lateArrivals;
    }

    public BigDecimal getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public void setTotalOvertimeHours(BigDecimal totalOvertimeHours) {
        this.totalOvertimeHours = totalOvertimeHours;
    }


    public BigDecimal getTotalPresents() {
        return totalPresents;
    }

    public void setTotalPresents(BigDecimal totalPresents) {
        this.totalPresents = totalPresents;
    }


}
