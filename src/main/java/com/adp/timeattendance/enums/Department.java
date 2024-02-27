package com.adp.timeattendance.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Department implements GrantedAuthority {

	HR, GBS, GPT, BOO, GSO;

	@Override
	public String getAuthority() {
		return this.name();
	}
}
