package com.adp.timeattendance.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class AttendanceReport {
	
	private Integer id;
	
	private String name;
	
	private BigDecimal LateArrivals;
	
	private BigDecimal TotalOvertimeHours;
	
	private BigDecimal TotalAbsents;
	
	private BigDecimal TotalPresents;

	public AttendanceReport() {
		super();
	}

	public AttendanceReport(Integer id, String name, BigDecimal lateArrivals, BigDecimal totalOvertimeHours,
			BigDecimal totalAbsents, BigDecimal totalPresents) {
		super();
		this.id = id;
		this.name = name;
		LateArrivals = lateArrivals;
		TotalOvertimeHours = totalOvertimeHours;
		TotalAbsents = totalAbsents;
		TotalPresents = totalPresents;
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
		return LateArrivals;
	}

	public void setLateArrivals(BigDecimal lateArrivals) {
		LateArrivals = lateArrivals;
	}

	public BigDecimal getTotalOvertimeHours() {
		return TotalOvertimeHours;
	}

	public void setTotalOvertimeHours(BigDecimal totalOvertimeHours) {
		TotalOvertimeHours = totalOvertimeHours;
	}

	public BigDecimal getTotalAbsents() {
		return TotalAbsents;
	}

	public void setTotalAbsents(BigDecimal totalAbsents) {
		TotalAbsents = totalAbsents;
	}

	public BigDecimal getTotalPresents() {
		return TotalPresents;
	}

	public void setTotalPresents(BigDecimal totalPresents) {
		TotalPresents = totalPresents;
	}

	
	
}
