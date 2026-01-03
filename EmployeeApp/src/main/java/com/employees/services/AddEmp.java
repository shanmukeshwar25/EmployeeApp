
package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.enums.Roles;
import com.employees.model.Employee;
import com.employees.utils.Utils;

public class AddEmp {

	// adding new employee into the JSON file
	public void addEmployee() {
		Scanner sc = new Scanner(System.in);
		EmpDAO dao = new EmpDAOImp();
		int ID = AutoID.autoId();
		Employee e = new Employee();
		String id = "EMP" + ID;
		e.setId(id);

		System.out.print("Enter first name: ");
		String firstname = sc.next();

		System.out.print("Enter last name: ");
		String lastname = sc.next();

		String name = firstname + " " + lastname;
		e.setName(name);

		e.setPass(Utils.hashPass(Utils.pass));

		System.out.print("Enter the date of birth (dd-MM-yyyy) : ");
		String dob = sc.next();
		Utils.validateDOB(dob, e);

		System.out.print("Enter Address: ");
		String address = sc.next();
		e.setAddress(address);

		System.out.print("Enter email: ");
		String email = sc.next();
		Utils.validateMail(email, e);

		boolean valid = false;
		while (!valid) {
			System.out.print("Available roles : ");
			for (Roles role : Roles.values()) {
				System.out.print(role + " ");
			}
			System.out.println();
			System.out.print("Enter role: ");
			String role = sc.next().toUpperCase();
			valid = e.setRole(role);
			if (!valid) {
				System.out.println("Invalid role re-enter again");
			}
		}

		System.out.print("Enter Department : ");
		String depname = sc.next();
		e.setdepName(depname);

		dao.putdata(e.getId(), e.getName(), e.getPass(), e.getDOB(), e.getAddress(), e.getEmail(), e.getRole(),
				e.getdepName());
		dao.viewEmp();

		System.out.println("Employee with Id : " + e.getId() + " added successfully\n");
	}

}
