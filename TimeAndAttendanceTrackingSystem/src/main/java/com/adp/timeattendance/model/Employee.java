package com.adp.timeattendance.model;


import com.adp.timeattendance.enums.Department;
import com.adp.timeattendance.enums.JobTitle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Employee_G5_Jan16")
public class Employee {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;


	@Column(nullable = false)
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column(columnDefinition = "VARCHAR(50)")
	@Enumerated(EnumType.STRING)
	private JobTitle jobTitle;

	@Column(columnDefinition = "VARCHAR(50)")
	@Enumerated(EnumType.STRING)
	private Department department;
	
	public Employee() {
		super();
	}
	
	public Employee(Integer id, String name, String email, String phone, JobTitle jobTitle, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.jobTitle = jobTitle;
		this.department = department;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public JobTitle getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", jobTitle="
				+ jobTitle + ", department=" + department + "]";
	}
	
	
}
