


package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;

public class ViewEmp {

	EmpDAO view = new EmpDAOImp();
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById() {
		try {
			String id = null;
			if (!Checkoper.role.equals("USER")) {
				System.out.print("Enter the ID to view: ");
				id = sc.next();
			} else {
				id = Checkoper.empid;
			}
			view.readById(id);
		} catch (NumberFormatException ex) {
			System.out.println("Please enter only numbers for ID, Age");
		}
	}

	// viewing all employees
	public void viewallEmployee() {
		view.readData();
	}
}


