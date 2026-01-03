package com.employees.model;
import java.util.ArrayList;
import java.util.List;

import com.employees.enums.Roles;

public class Employee {
	private List<String> arr = new ArrayList<>();
	private String id;
	private String name;
	private String dob;
	private String pass;
	private String address;
	private String email;
	private String depname;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public String getdepName() {
		return depname;
	}

	public String getEmail() {
		return email;
	}
	public List<String> getRole() {
		return arr;
	}
	public String getAddress() {
		return address;
	}
	public String getDOB() {
		return dob;
	}
	public String getPass() {
		return pass;
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	public void setPass(String pass) {
		this.pass=pass;
	}
	public void setdepName(String depname) {
		this.depname=depname;;
	}
	public void setDOB(String dob) {
		this.dob=dob;
	}

	public void setAddress(String address) {
		this.address=address;
	}

	public void setEmail(String email) {
		this.email=email;
	}
	public boolean setRole(String role) {
		if(role==null) return false;
		try {
			Roles choice;
			choice = Roles.valueOf(role.toUpperCase());
			this.arr.add(role);
			return true;
			
		}
		catch(IllegalArgumentException e) {
			return false;
		}
	}
}