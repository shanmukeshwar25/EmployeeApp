package com.employees.mainclass;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.JdbcEmpDAOImp;
import com.employees.storage.StorageSelection;
import com.employees.storage.StorageType;

public class Main {
	public static void main(String args[]) {
		StorageType type = StorageSelection.storageSelection();
		EmpDAO empdao;
		if (type.equals(StorageType.FILE)) {
			empdao = new EmpDAOImp();
		} else {
			empdao = new JdbcEmpDAOImp();
		}

		Login.start(empdao);

	}
}
