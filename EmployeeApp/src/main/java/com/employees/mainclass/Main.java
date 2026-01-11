package com.employees.mainclass;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.JdbcEmpDAOImp;
import com.employees.storage.StorageSelection;
import com.employees.storage.StorageType;

public class Main {
	public static void main(String args[]) {
//		DatabaseConnecttion.startConnection();
//		EmpDAO emp = new JdbcEmpDAOImp();
//		List<String> l = new ArrayList<>();
//		l.add("ADMIN");
//		emp.addEmp("Alice Smith","admin", "15-09-2005", "New York", "alihce.s@hexample.com",l, "Engineering");
		
		StorageType type = StorageSelection.storageSelection();
		EmpDAO empdao;
		if(type.equals(StorageType.FILE)) {
			empdao = new EmpDAOImp();
		} 
		else {
			empdao = new JdbcEmpDAOImp();
		}
		
		Login.start(empdao);
		
	}
}
