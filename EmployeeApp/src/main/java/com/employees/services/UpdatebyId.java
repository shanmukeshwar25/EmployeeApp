
package com.employees.services;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.model.Employee;
import com.employees.utils.Utils;

public class UpdatebyId {

	// updating details of specific ID
	public void updatebyid() {
			Scanner sc = new Scanner(System.in);
			boolean valid = true;
			String id = null;
			Employee e = new Employee();
			while (valid) {
				System.out.print("Enter Employee ID to update: ");
				id = sc.next();
				if (!LoginServices.checkExists(id)) {
					System.out.println("Employee with ID:" + id + " does not exists" + " enter the valid ID");
				} else {
					e.setId(id);
					valid = false;
				}
			}
			sc.nextLine();
			EmpDAO up = new EmpDAOImp();
			System.out.print("Enter the first name: ");
			String firstname = sc.next();
			System.out.print("Enter last name: ");
			String lastname = sc.next();
			String name = firstname + " " + lastname;
			e.setName(name);

			System.out.print("Enter the date of birth (dd-MM-yyyy) : ");
			String dob = sc.next();
			Utils.validateDOB(dob, e);

			System.out.print("Enter Address: ");
			String address = sc.next();
			e.setAddress(address);

			System.out.print("Enter email: ");
			String email = sc.next();
			Utils.validateMail(email, e);

			System.out.print("Enter Department : ");
			String depname = sc.next();
			e.setdepName(depname);

			up.updatebyId(e.getId(), e.getName(), e.getDOB(), e.getAddress(), e.getEmail(), e.getdepName());
			
		} 

	// Updates the logged-in user's address and email
	public void updateUserbyid() {
			String id = LoginServices.empid;
			if (!LoginServices.checkExists(id)) {
				System.out.println("Employee with ID:" + id + " does not exists");
				return;
			}
			Employee e = new Employee();
			e.setId(id);
			Scanner sc = new Scanner(System.in);
			EmpDAO dao = new EmpDAOImp();

			System.out.print("Enter Address: ");
			String address = sc.nextLine();
			e.setAddress(address);

			System.out.print("Enter email: ");
			String email = sc.next();
			Utils.validateMail(email, e);

			dao.updateUserbyId(id, address, email);
			dao.viewEmp();
			
		} 
	}
