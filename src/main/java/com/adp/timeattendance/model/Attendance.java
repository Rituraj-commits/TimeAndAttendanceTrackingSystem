package com.adp.timeattendance.model;

import java.util.Date;

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

@Entity
@Table(name="Attendance_Group5_Jan16")
public class Attendance {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "empId")
	private Employee employeeId;
	
	@Column
	private Date attendanceDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Attendance() {
		super();
	}

	public Attendance(Integer id, Employee employeeId, Date attendanceDate, Status status) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.attendanceDate = attendanceDate;
		this.status = status;
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

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", employeeId=" + employeeId + ", attendanceDate=" + attendanceDate
				+ ", status=" + status + "]";
	}
	
	
	
}
