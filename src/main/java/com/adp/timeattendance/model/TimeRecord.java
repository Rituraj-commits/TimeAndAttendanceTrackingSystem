package com.adp.timeattendance.model;

import java.sql.Timestamp;

import com.adp.timeattendance.enums.ClockEvent;

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

@Entity
@Table(name="TimeRecord_Group5_Jan16")
public class TimeRecord {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "empId")
	private Employee employeeId;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ClockEvent clockEvent;
	
	@Column
	private Timestamp clockTime;
	
	@Column
	private Integer overtimeHours;
	
	@Column
	private boolean lateArrival;

	public TimeRecord() {
		super();
	}

	public TimeRecord(Integer id, Employee employeeId, ClockEvent clockEvent, Timestamp clockTime, Integer overtimeHours,
			boolean lateArrival) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.clockEvent = clockEvent;
		this.clockTime = clockTime;
		this.overtimeHours = overtimeHours;
		this.lateArrival = lateArrival;
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

	public boolean isLateArrival() {
		return lateArrival;
	}

	public void setLateArrival(boolean lateArrival) {
		this.lateArrival = lateArrival;
	}

	@Override
	public String toString() {
		return "TimeRecord [id=" + id + ", employeeId=" + employeeId + ", clockEvent=" + clockEvent + ", clockTime="
				+ clockTime + ", overtimeHours=" + overtimeHours + ", lateArrival=" + lateArrival + "]";
	}
	
	
}
