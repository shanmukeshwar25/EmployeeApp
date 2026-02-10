
package com.employees.services;

import java.util.Optional;
import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.model.Employee;
import com.employees.model.Session;
import com.employees.utils.Utils;

public class UpdateEmployee {

	// updating details of specific ID
	public void updateEmployeeById(EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		boolean valid = true;

		String id = null;

		Employee e = null;
		while (valid) {
			System.out.print("Enter Employee ID to update: ");
			id = sc.next().toUpperCase();
		
		if(dao.checkExists(id)) {
			Optional<Employee> opt = dao.findById(id);
			if (opt.isEmpty()) {
			    System.out.println("Employee not found");
			    return;
			}
			e = opt.get();
			e.setId(id);
			valid = false;
		}
		else {
			System.out.println("Employee with ID:" + id + " does not exists");
		}
		
		String firstname;
		do {
		    System.out.print("Enter the first name: ");
		    firstname = sc.next();
		} while (!Utils.validateName(firstname));
		
		String lastname;
		do {
		    System.out.print("Enter last name: ");
		    lastname = sc.next();
		} while (!Utils.validateName(lastname));

		String name = firstname + " " + lastname;
		e.setName(name);

		boolean validob = false;
		while (!validob) {
			System.out.print("Enter the date of birth (dd-MM-yyyy) : ");
			String dob = sc.next();
			if (Utils.validateDOB(dob)) {
				validob = true;
				e.setDOB(dob);
			}
		}

		String address;
		do {
		    System.out.print("Enter Address: ");
		    address = sc.nextLine();
		} while (!Utils.validateAddress(address));


		boolean validmail = false;
		while (!validmail) {
			System.out.print("Enter email: ");
			String email = sc.next();
			if (Utils.validateMail(email)) {
				validmail = true;
				e.setEmail(email);
			}
		}

		
		String depname;
		do {
		    System.out.print("Enter Department : ");
		    depname = sc.next();
		} while (!Utils.validateDepartment(depname));


		dao.updateById(e.getId(), e.getName(), e.getDOB(), e.getAddress(), e.getEmail(), e.getdepName());
		dao.viewEmployees();
	  }
	}

	// Updates the logged-in user's address and email
	public void updateUserbyid(EmpDAO dao,Session session) {
        
		String id = session.getId();
		Employee e = null;
		Optional<Employee> opt = dao.findById(id);
		
		if (opt.isEmpty()) {
		    System.out.println("Employee not found");
		    return;
		}
		e = opt.get();
		e.setId(id);

		Scanner sc = new Scanner(System.in);

		String address;
		do {
		    System.out.print("Enter Address: ");
		    address = sc.nextLine();
		} while (!Utils.validateAddress(address));


		boolean validmail = false;
		while (!validmail) {
			System.out.print("Enter email: ");
			String email = sc.next();
			if (Utils.validateMail(email)) {
				validmail = true;
				e.setEmail(email);
			}
		}

		dao.updateUserById(e.getId(), e.getAddress(), e.getEmail());
        dao.viewEmployeeById(e.getId());
	}
}
