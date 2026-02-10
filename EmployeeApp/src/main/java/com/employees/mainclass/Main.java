package com.employees.mainclass;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.FileEmployeeDAO;
import com.employees.dao.JdbcEmployeeDAO;
import com.employees.storage.StorageSelection;
import com.employees.storage.StorageType;

public class Main {
	public static void main(String args[]) throws IOException, ParseException {
		StorageType type = StorageSelection.storageSelection();
		EmpDAO empdao;
		if (type.equals(StorageType.FILE)) {
			empdao = new FileEmployeeDAO();
		} else {
			empdao = new JdbcEmployeeDAO();
		}

		Login.start(empdao);

	}
}
