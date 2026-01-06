package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;

public class ViewEmp {

	EmpDAO view = new EmpDAOImp();
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById() {
			String id = null;
			if (!ServerSideValidation.role.equals("USER")) {
				System.out.print("Enter the ID to view: ");
				id = sc.next();
			} else {
				id = ServerSideValidation.empid;
			}
			view.viewEmpById(id);
		} 

	// viewing all employees
	public void viewallEmployee() {
		view.viewEmp();
	}
}


