package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;
import com.employees.exception.EmployeeDoesNotExistException;

public class ViewEmp {

	EmpDAO dao = new EmpDAOImp();
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById() {

		String id = null;
		if (ServerSideValidation.role.equals("USER")) {
			id = ServerSideValidation.empid;
		} else {
			System.out.print("Enter the ID to view: ");
			id = sc.next();
		}
		
		dao.viewEmpById(id);
//		boolean present = ServerSideValidation.checkExists(id);
//		
//		if (present) {
//			dao.viewEmpById(id);
//		} else {
//			throw new EmployeeDoesNotExistException("Employee does not exist");
//		}
	}

	// viewing all employees
	public void viewallEmployee() {
		 boolean hasData = dao.viewEmp();
		    if (!hasData) {
		        System.out.println("No employees found");
		    }
	}
}
