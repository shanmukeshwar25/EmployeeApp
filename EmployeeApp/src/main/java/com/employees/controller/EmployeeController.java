package com.employees.controller;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.exception.EmployeeNotFoundException;
import com.employees.exception.ServiceException;
import com.employees.exception.ValidationException;
import com.employees.model.Session;
import com.employees.services.EmployeeServices;

public class EmployeeController {
	private static final Scanner sc = new Scanner(System.in);
	EmployeeServices employeeService =new EmployeeServices();
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
	        System.out.println("Enter role:");
	        String role = sc.nextLine().toUpperCase();
	        
	        
	        
	}

	public void deleteEmployeeById(EmpDAO dao) {
			System.out.println("Enter Id to Delete:");
			String id = sc.next().toUpperCase();
			try {
//				employeeService.delete(dao, id);
				System.out.println("Employee deleted succesfully");
			} catch (ValidationException e) {
				System.out.println("error while delete employee:" + e.getMessage());
			} catch (EmployeeNotFoundException e) {
	  			System.out.println(e.getMessage());
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
			}
		}

	public void grantRole(EmpDAO dao) {

	}

	public void revokeRole(EmpDAO dao) {

	}

	public void resetPassword(EmpDAO dao) {

	}

	public void setPassword(EmpDAO dao, Session session) {

	}

	public void updateEmployeeById(EmpDAO dao) {

	}

	public void updateUserbyid(EmpDAO dao, Session session) {

	}

	public void viewEmployeeById(EmpDAO dao, Session session) {

	}

	public void viewallEmployee(EmpDAO dao) {

	}
}
