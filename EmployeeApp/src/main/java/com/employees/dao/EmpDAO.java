package com.employees.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.employees.model.Employee;
import com.employees.model.LoginResult;

public interface EmpDAO {
	void addEmployee(String name,String pass,String dob,String address,String email,List<String> role,String depname);
	void deleteId(String id) throws SQLException;
	void viewEmployees();
	void viewEmployeeById(String id);
	boolean checkExists(String id);
	void updateById(String id,String name,String DOB,String address,String email,String depname);
	void setPassword(String id,String password);
	void updateUserById(String id,String address,String email);
	void grantRole(String id,String role);
	void revokeRole(String id,String role);
	Optional<Employee> findById(String id);
	LoginResult checkLogin(String id, String p);
}
