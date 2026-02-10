package com.employees.services;

import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.model.Session;

public class ViewEmployee {
	Scanner sc = new Scanner(System.in);

	// for viewing employees of a specific id
	public void viewEmployeeById(EmpDAO dao,Session session) {

		String id = null;
		if (session.getRoles().size()<=1 && session.getRoles().get(0).equals("USER")) {
			id = session.getId();
		} else {
			System.out.print("Enter the ID to view: ");
		   id = sc.next().toUpperCase();
		   if(!dao.checkExists(id)) {
				System.out.println("employee with id "+id+" does not exists");
				return;
			}
		}
		dao.viewEmployeeById(id);
	}

	// viewing all employees
	public void viewallEmployee(EmpDAO dao) {
		 dao.viewEmployees();
	}
}
