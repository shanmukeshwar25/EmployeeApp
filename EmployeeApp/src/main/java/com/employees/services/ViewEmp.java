package com.employees.services;

import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;

public class ViewEmp {
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById(EmpDAO dao) {

		String id = null;
		if (Login.result.getRole().size()<=1 && Login.result.getRole().get(0).equals("USER")) {
			id = Login.result.getId();
		} else {
			System.out.print("Enter the ID to view: ");
			id = sc.next().toUpperCase();
		}
		
		dao.viewEmpById(id);
	}

	// viewing all employees
	public void viewallEmployee(EmpDAO dao) {
		 boolean hasData = dao.viewEmp();
		    if (!hasData) {
		        System.out.println("No employees found");
		    }
	}
}
