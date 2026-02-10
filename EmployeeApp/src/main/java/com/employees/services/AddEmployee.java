
package com.employees.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import com.employees.dao.EmpDAO;
import com.employees.utils.EmployeeIdGenerator;
import com.employees.enums.Roles;
import com.employees.logger.EmployeeLogger;
import com.employees.model.Employee;
import com.employees.utils.GeneratePassword;
import com.employees.utils.Utils;

public class AddEmployee {

	// adding new employee into the JSON file
	public void addEmployee(EmpDAO dao) throws IOException, ParseException {
		Logger log = EmployeeLogger.getLog();
		Scanner sc = new Scanner(System.in);
		int ID = EmployeeIdGenerator.generateNextEmployeeId();
		Employee emp = new Employee();
		String id = "EMP" + ID;
		emp.setId(id);

		String firstname;
		do {
			System.out.print("Enter firstname : ");
			firstname = sc.nextLine();
		} while (!Utils.validateName(firstname));

		String lastname;
		do {
			System.out.print("Enter lastname : ");
			lastname = sc.nextLine();
		} while (!Utils.validateName(lastname));

		String name = firstname + " " + lastname;
		log.info("add employee request recieved for name {}",emp.getName());
		emp.setName(name);

		String password = "emp" + GeneratePassword.generatePassword();
		System.out.println("the default password is : " + password);
		emp.setPass(Utils.hashPass(password));

		boolean validob = false;
		String dob = null ;
		while (!validob) {
			System.out.print("Enter the date of birth (dd-MM-yyyy) : ");
			dob  = sc.nextLine();
			if (Utils.validateDOB(dob)) {
				validob = true;
				emp.setDOB(dob);
			}
		}

		String address;
		do {
			System.out.print("Enter Address : ");
			address = sc.nextLine();
		} while (!Utils.validateAddress(address));
		emp.setAddress(address);

		boolean validmail = false;
		String email = null;
		while (!validmail) {
			System.out.print("Enter email: ");
			email =  sc.nextLine();
			if (Utils.validateMail(email)) {
				validmail = true;
				emp.setEmail(email);
				break;
			}
		}

		boolean valid = false;
		List<String> roles = new ArrayList<>();
		while (!valid) {
			System.out.print("Available roles : ");
			for (Roles role : Roles.values()) {
				System.out.print(role + " ");
			}
			System.out.println();
			System.out.print("Enter role: ");
			String role = sc.nextLine().toUpperCase();
			roles.add(role);
			valid = emp.setRole(role);
			if (!valid) {
				System.out.println("Invalid role re-enter again");
			}
		}

		String depname;
		do {
			System.out.print("Enter department : ");
			depname = sc.nextLine();
		} while (!Utils.validateDepartment(depname));
		emp.setdepName(depname);
		try {
			dao.addEmployee(name, password, dob , address, email, roles, depname);
			dao.viewEmployees();
		} catch (Exception er) {
			log.error("Failed to add Employee for ID {}",emp.getId(),er.getMessage());
		}

	}

}
