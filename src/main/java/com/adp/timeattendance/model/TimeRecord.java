package com.adp.timeattendance.model;

import java.sql.Date;
import java.sql.Time;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

@Entity
@Table(name="TimeRecord_G5_Jan16")
public class TimeRecord {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "empId")
	private Employee employeeId;
	
	@Column
	private Time clockIn;
	
	@Column
	private Time clockOut;
	
	@Column
	private Date attendanceDate;

	public TimeRecord() {
		super();
	}

	public TimeRecord(Integer id, Employee employeeId, Time clockIn, Time clockOut, Date attendanceDate) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clockIn = clockIn;
		this.clockOut = clockOut;
		this.attendanceDate = attendanceDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
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
