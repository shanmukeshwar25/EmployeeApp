package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;

public class ViewEmp {

	EmpDAO view = new EmpDAOImp();
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById() {
			String id = null;
			if (!LoginServices.role.equals("USER")) {
				System.out.print("Enter the ID to view: ");
				id = sc.next();
			} else {
				id = LoginServices.empid;
			}
			view.viewEmpById(id);
		} 

	// viewing all employees
	public void viewallEmployee() {
		view.viewEmp();
	}
}


