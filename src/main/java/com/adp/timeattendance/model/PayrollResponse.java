package com.adp.timeattendance.model;

import java.util.Date;

public class PayrollResponse {

	private Double totalAmount;

	private Integer overtimeHours;

	private Integer presentDays;

	private Double regularPay;

	private Double overtimePay;


	private String payrollDate;

	public PayrollResponse() {
		super();
	}

	public PayrollResponse(Double totalAmount, Integer overtimeHours, Integer presentDays, Double regularPay, Double overtimePay, String payrollDate) {
		this.totalAmount = totalAmount;
		this.overtimeHours = overtimeHours;
		this.presentDays = presentDays;
		this.regularPay = regularPay;
		this.overtimePay = overtimePay;
		this.payrollDate = payrollDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(Integer overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public Integer getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(Integer presentDays) {
		this.presentDays = presentDays;
	}

	public Double getRegularPay() {
		return regularPay;
	}

	public void setRegularPay(Double regularPay) {
		this.regularPay = regularPay;
	}

	public Double getOvertimePay() {
		return overtimePay;
	}

	public void setOvertimePay(Double overtimePay) {
		this.overtimePay = overtimePay;
	}

	public String getPayrollDate() {
		return payrollDate;
	}

	public void setPayrollDate(String payrollDate) {
		this.payrollDate = payrollDate;
	}

	@Override
	public String toString() {
		return "PayrollResponse{" +
				"totalAmount=" + totalAmount +
				", overtimeHours=" + overtimeHours +
				", presentDays=" + presentDays +
				", regularPay=" + regularPay +
				", overtimePay=" + overtimePay +
				", payrollDate=" + payrollDate +
				'}';
	}
}

