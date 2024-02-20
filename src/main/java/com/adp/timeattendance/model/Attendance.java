package com.adp.timeattendance.model;

import java.sql.Date;

import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Attendance_G5_Jan16")
public class Attendance {
	
	@Id
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "empId")
	private Employee employeeId;
	
	private Integer overtimeHours;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private LateArrivalStatus lateArrival;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private Status status;
	
	@Column
	private Date attendanceDate;

	public Attendance() {
		super();
	}

	public Attendance(Integer id, Employee employeeId, Integer overtimeHours, LateArrivalStatus lateArrival,
			Status status, Date attendanceDate) {
		super();
		this.id = id;
		this.employeeId = employeeId;
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

	public Integer getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(Integer overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public LateArrivalStatus getLateArrival() {
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
