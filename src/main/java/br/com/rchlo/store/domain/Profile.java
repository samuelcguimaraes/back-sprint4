package br.com.rchlo.store.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Profile implements GrantedAuthority {
	ROLE_GUEST,
	ROLE_ADMIN;
	
	@Override
	public String getAuthority() {
		return this.name();
	}
}
