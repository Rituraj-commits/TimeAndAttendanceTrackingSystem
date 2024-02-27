package com.adp.timeattendance.enums;

import org.springframework.security.core.GrantedAuthority;

public enum JobTitle implements GrantedAuthority {
	MANAGER,
	EMPLOYEE;

	@Override
	public String getAuthority() {
		return this.name();
	}
}
