package com.adp.timeattendance.model;

import java.sql.Date;
import java.sql.Timestamp;

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
	

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private ClockEvent clockEvent;
	
	@Column
	private Timestamp clockTime;
	
	@Column
	private Integer overtimeHours;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private LateArrivalStatus lateArrival;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private Status status;

	@Column
	private Date attendanceDate;

	public TimeRecord() {
		super();
	}

	public TimeRecord(Integer id, Employee employeeId, ClockEvent clockEvent, Timestamp clockTime, Integer overtimeHours, LateArrivalStatus lateArrival, Status status, Date attendanceDate) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clockEvent = clockEvent;
		this.clockTime = clockTime;
		this.overtimeHours = overtimeHours;
		this.lateArrival = lateArrival;
		this.status = status;
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

	public ClockEvent getClockEvent() {
		return clockEvent;
	}

	public void setClockEvent(ClockEvent clockEvent) {
		this.clockEvent = clockEvent;
	}

	public Timestamp getClockTime() {
		return clockTime;
	}

	public void setClockTime(Timestamp clockTime) {
		this.clockTime = clockTime;
	}

	public Integer getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(Integer overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public LateArrivalStatus isLateArrival() {
		return lateArrival;
	}

	public void setLateArrival(LateArrivalStatus lateArrival) {
		this.lateArrival = lateArrival;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
}
