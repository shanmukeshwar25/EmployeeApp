
package com.employees.services;

import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;
import com.employees.model.Employee;
import com.employees.utils.Utils;

public class UpdatebyId {

	// updating details of specific ID
	public void updatebyid(EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		boolean valid = true;

		String id = null;

		Employee e = null;
		while (valid) {
			System.out.print("Enter Employee ID to update: ");
			id = sc.next().toUpperCase();
			if (!dao.checkExists(id)) {
				System.out.println("Employee with ID:" + id + " does not exists");
			} else {
				e = dao.findById(id);
				e.setId(id);
				valid = false;
			}
		}
		System.out.print("Enter the first name: ");
		String firstname = sc.next();
		System.out.print("Enter last name: ");
		String lastname = sc.next();
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

		System.out.print("Enter Address: ");
		String address = sc.next();
		e.setAddress(address);

		boolean validmail = false;
		while (!validmail) {
			System.out.print("Enter email: ");
			String email = sc.next();
			if (Utils.validateMail(email)) {
				validmail = true;
				e.setEmail(email);
			}
		}

		System.out.print("Enter Department : ");
		String depname = sc.next();
		e.setdepName(depname);

		dao.updatebyId(e.getId(), e.getName(), e.getDOB(), e.getAddress(), e.getEmail(), e.getdepName());
		dao.viewEmp();
	}

	// Updates the logged-in user's address and email
	public void updateUserbyid(EmpDAO dao) {
        
		String id = Login.result.getId();
		Employee e = null;
		e = dao.findById(id);
		e.setId(id);

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Address: ");
		String address = sc.nextLine();
		e.setAddress(address);

		boolean validmail = false;
		while (!validmail) {
			System.out.print("Enter email: ");
			String email = sc.next();
			if (Utils.validateMail(email)) {
				validmail = true;
				e.setEmail(email);
			}
		}

		dao.updateUserbyId(e.getId(), e.getAddress(), e.getEmail());
        dao.viewEmpById(e.getId());
	}
}
