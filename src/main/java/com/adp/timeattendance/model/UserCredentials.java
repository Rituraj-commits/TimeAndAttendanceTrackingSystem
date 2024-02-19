package com.adp.timeattendance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="UserCredentials_Group5_Jan16")
public class UserCredentials {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="userId")
	private Employee employee;

	private String username;
	
	private String password;

	
	public UserCredentials() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCredentials(Integer id, Employee employee, String username, String password) {
		super();
		this.id = id;
		this.employee = employee;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", employee=" + employee + ", username=" + username + ", password="
				+ password + "]";
	}
	
	
	
}