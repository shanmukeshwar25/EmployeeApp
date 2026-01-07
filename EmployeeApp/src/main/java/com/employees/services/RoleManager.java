package com.employees.services;

import java.io.File;
import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;
import com.employees.model.Employee;
import com.employees.utils.Utils;

public class RoleManager {
	
	// adding new role to employee
	public void grantRole() {
		Employee employee = new Employee();
		try(Scanner sc = new Scanner(System.in)){
        
		if (Utils.file.exists() && Utils.file.length() <= 2) {
			System.out.println("no employees found");
			return;
		}
			System.out.print("Enter the id to grant role: ");
			String id = sc.next();
			if (ServerSideValidation.checkExists(id)) {
				employee.setId(id);
			} else {
				System.out.println("Employee with id:" + id + " does not exist");
				return;
			}

			boolean valid = false;
			String role = null;
			while (!valid) {
				System.out.print("Enter new role: ");
				role = sc.next().toUpperCase();
				valid = employee.setRole(role);
				if (!valid) {
					System.out.println("Invalid role re-enter again");
				}
			}
		EmpDAO dao = new EmpDAOImp();
		dao.grantRole(id, role);
		}
		catch (Exception e) {
	        System.out.println("Something went wrong. Please try again.");
	    }
            
	}
	
	// undo the role assigned
	public void revokeRole() {
		Employee employee = new Employee();
		Scanner sc = new Scanner(System.in);
        
		if (Utils.file.exists() && Utils.file.length() <= 2) {
			System.out.println("no employees found");
			return;
		}
			System.out.print("Enter the id to revoke role: ");
			String id = sc.next();
			if (ServerSideValidation.checkExists(id)) {
				employee.setId(id);
			} else {
				System.out.println("Employee with id:" + id + " does not exist");
				return;
			}

			boolean valid = false;
			String role = null;
			while (!valid) {
				System.out.print("Enter new role: ");
				role = sc.next().toUpperCase();
				valid = employee.setRole(role);
				if (!valid) {
					System.out.println("Invalid role re-enter again");
				}
			}
		EmpDAO dao = new EmpDAOImp();
		dao.revokeRole(id, role);
	}
}
