package com.employees.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.enums.Roles;
import com.employees.exception.EmployeeNotFoundException;
import com.employees.exception.ServiceException;
import com.employees.exception.ValidationException;
import com.employees.model.Session;
import com.employees.services.EmployeeServices;

public class EmployeeController {
	private static final Scanner sc = new Scanner(System.in);
	EmployeeServices employeeService = new EmployeeServices();

	public void addEmployee(EmpDAO dao) {

		System.out.println("Enter first name:");
		String first = sc.nextLine();

		System.out.println("Enter last name:");
		String last = sc.nextLine();

		System.out.println("Enter DOB (dd-MM-yyyy):");
		String dob = sc.nextLine();

		System.out.println("Enter address:");
		String address = sc.nextLine();

		System.out.println("Enter email:");
		String email = sc.nextLine();

		System.out.println("Enter department:");
		String dept = sc.nextLine();

		List<String> roles = new ArrayList<>();

		for (Roles r : Roles.values()) {
			System.out.print(r + " ");
		}

		System.out.println("\nEnter role:");
		roles.add(sc.nextLine().toUpperCase());
		try {
			String password = employeeService.addEmployee(dao, first, last, dob, address, email, roles, dept);
			System.out.println("Employee added successfully");
			System.out.println("Your password:" + password);

		} catch (ValidationException e) {
			System.out.println("error while inserting an employee " + e.getMessage());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteEmployeeById(EmpDAO dao) {
		System.out.println("Enter Id to Delete:");
		String id = sc.next().toUpperCase();
		try {
			employeeService.delete(dao, id);
			System.out.println("Employee deleted succesfully");
		} catch (ValidationException e) {
			System.out.println("error while deleting employee:" + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	public void grantRole(EmpDAO dao) {
		System.out.println("Enter Id:");
		String id = sc.next().toUpperCase();
		System.out.println("Enter role:");
		String role = sc.next().toUpperCase();
		try {
			employeeService.grantRole(dao, id, role);
		} catch (ValidationException e) {
			System.out.println("error while assign role:" + e.getMessage());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void revokeRole(EmpDAO dao) {
		System.out.println("Enter Id:");
		String id = sc.next().toUpperCase();
		System.out.println("Enter role:");
		String role = sc.next().toUpperCase();
		try {
			employeeService.revokeRole(dao, id, role);
			System.out.println("role revoked succesfully");
		} catch (ValidationException e) {
			System.out.println("error while assign role:" + e.getMessage());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void resetPassword(EmpDAO dao, Session session) {
		System.out.println("Enter Id:");
		String id = sc.next().toUpperCase();
		try {
			String newPass = employeeService.setPassword(dao, id);
			System.out.println("password set successful");
			System.out.println("new password is : " + newPass);
		} catch (ValidationException e) {
			System.out.println("error while setting the password:" + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println("error:" + e.getMessage());
		}
	}

	public void changePassword(EmpDAO dao, Session session) {
		System.out.print("Enter new password: ");
		String pass = sc.next();

		System.out.print("Re-enter password: ");
		String repass = sc.next();
		try {
			employeeService.changePassword(dao, session.getId(), pass, repass);
			System.out.println("Password changed successful");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateEmployeeById(EmpDAO dao) {
		System.out.print("Enter ID: ");
		String id = sc.next().toUpperCase();
		sc.nextLine();

		System.out.println("Enter first name:");
		String first = sc.nextLine();

		System.out.println("Enter last name:");
		String last = sc.nextLine();

		System.out.println("Enter DOB (dd-MM-yyyy):");
		String dob = sc.nextLine();

		System.out.println("Enter address:");
		String address = sc.nextLine();

		System.out.println("Enter email:");
		String email = sc.nextLine();

		System.out.println("Enter department:");
		String dept = sc.nextLine();

		try {
			employeeService.updateEmployeeById(dao, id, first, last, dob, address, email, dept);
		} catch (ValidationException e) {
			System.out.println("error while update " + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println("error:" + e.getMessage());
		}
	}

	public void updateUserbyid(EmpDAO dao, Session session) {
		System.out.print("Address: ");
		String address = sc.nextLine();

		System.out.print("Email: ");
		String email = sc.nextLine();

		try {
			employeeService.updateUserbyid(dao, session, address, email);
		} catch (ValidationException e) {
			System.out.println("error while update : " + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			System.out.println("error:" + e.getMessage());
		}

	}

	public void viewEmployeeById(EmpDAO dao, Session session) {
		String id;
		if (!session.getRoles().contains("USER")) {
			System.out.print("Enter ID to view: ");
			id = sc.next().toUpperCase();
		} else
			id = session.getId();

		try {
			employeeService.viewEmployeeById(dao, session, id);
		} catch (Exception e) {
			System.out.println("Unable to fetch employees");
		}

	}

	public void viewallEmployee(EmpDAO dao) {
		try {
			employeeService.viewallEmployee(dao);
		} catch (Exception e) {
			System.out.println("Unable to fetch employees");
		}
	}
	
	public void fetchInActive(EmpDAO dao) {
		try {
			employeeService.fetchInActive(dao);
		}
		catch (Exception e) {
			System.out.println("Unable to fetch inActive employees");
		}
	}

}
