package com.employees.model;

import java.util.List;

public class Session {
	private final LoginResult currentUser;

	public Session(LoginResult currentUser) {
		this.currentUser = currentUser;
	}

	public String getId() {
		return currentUser.getId();
	}

	public List<String> getRoles() {
		return currentUser.getRole();
	}

	public LoginResult getUser() {
		return currentUser;
	}
}
