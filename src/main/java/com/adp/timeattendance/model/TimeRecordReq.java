package com.adp.timeattendance.model;

import java.sql.Time;
import java.sql.Date;

public class TimeRecordReq {
	
	private Integer employeeId;
	private Time clockTime;
	private Date attendanceDate;
	
	public TimeRecordReq() {
		super();
	}

	public TimeRecordReq(Integer employeeId, Time clockTime, Date attendanceDate) {
		super();
		this.employeeId = employeeId;
		this.clockTime = clockTime;
		this.attendanceDate = attendanceDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Time getClockTime() {
		return clockTime;
	}

	public void setClockTime(Time clockTime) {
		this.clockTime = clockTime;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	
	

}
