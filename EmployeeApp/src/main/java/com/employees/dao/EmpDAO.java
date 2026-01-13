package com.employees.dao;

import java.util.List;

import com.employees.model.Employee;
import com.employees.model.LoginResult;

public interface EmpDAO {
	void addEmp(String name,String pass,String dob,String address,String email,List<String> role,String depname);
	void deleteId(String id);
	boolean viewEmp();
	void viewEmpById(String id);
	boolean checkExists(String id);
	void updatebyId(String id,String name,String DOB,String address,String email,String depname);
	void setPass(String id,String password);
	void updateUserbyId(String id,String address,String email);
	void grantRole(String id,String role);
	void revokeRole(String id,String role);
	Employee findById(String id);
	LoginResult checkLogin(String id, String p);
}
