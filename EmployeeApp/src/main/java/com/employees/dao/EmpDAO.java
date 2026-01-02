
package com.employees.dao;

import java.util.List;

public interface EmpDAO {
	void putdata(String id,String name,String pass,String dob,String address,String email,List<String> role,String depname);
	void deleteId(String id);
	void readData();
	void readById(String id);
	void updatebyId(String id,String name,String DOB,String address,String email,String depname);
	void setPass(String id,String password);
	void updateUserbyId(String id,String address,String email);
	void grantRole(String id,String role);
	void revokeRole(String id,String role);
}
