package com.adp.timeattendance.model;

import java.sql.Time;
import java.sql.Date;

public class TimeRecordRequest {
    private Integer employeeId;
    private Time clockIn;
    private Time clockOut;
    private Date attendanceDate;

    public TimeRecordRequest(Integer employeeId, Time clockIn, Time clockOut, Date attendanceDate) {
        this.employeeId = employeeId;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.attendanceDate = attendanceDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Time getClockIn() {
        return clockIn;
    }

    public void setClockIn(Time clockIn) {
        this.clockIn = clockIn;
    }

    public Time getClockOut() {
        return clockOut;
    }

    public void setClockOut(Time clockOut) {
        this.clockOut = clockOut;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
