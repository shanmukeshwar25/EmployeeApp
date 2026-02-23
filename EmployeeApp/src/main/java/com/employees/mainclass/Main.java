package com.employees.mainclass;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.FileEmployeeDAOImp;
import com.employees.dao.JdbcEmployeeDAOImp;
import com.employees.storage.StorageSelection;
import com.employees.storage.StorageType;

public class Main {
	public static void main(String args[]) {
		StorageType type = StorageSelection.storageSelection();
		EmpDAO empdao;
		if (type.equals(StorageType.FILE)) {
			empdao = new FileEmployeeDAOImp();
		} else {
			empdao = new JdbcEmployeeDAOImp();
		}

		Login.start(empdao);

	}
}
