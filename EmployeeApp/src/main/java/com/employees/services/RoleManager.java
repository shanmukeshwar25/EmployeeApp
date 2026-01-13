package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.ServerSideValidation;
import com.employees.model.Employee;
import com.employees.utils.Utils;

public class RoleManager {

	// adding new role to employee
	public void grantRole(EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		if (Utils.file.exists() && Utils.file.length() <= 2) {
			System.out.println("no employees found");
			return;
		}

		System.out.print("Enter the id to grant role: ");
		String id = sc.next().toUpperCase();

		if (!dao.checkExists(id)) {
			System.out.println("Employee with id:" + id + " does not exist");
			return;
		}

		Employee ems = dao.findById(id);
		System.out.println("Available roles: " + ems.getRole());

		boolean valid = false;
		String role;
		while (!valid) {
			System.out.print("Enter valid role: ");
			role = sc.next().toUpperCase();

			if (ems.getRole().contains(role)) {
				System.out.println("cannot assign the same role again");
				return;
			}

			valid = ems.setRole(role);
			if (valid) {
				dao.grantRole(id, role);
			} else {
				System.out.println("Invalid role re-enter again");
			}
		}
	}

	// undo the role assigned
	public void revokeRole(EmpDAO dao) {
		Scanner sc = new Scanner(System.in);

		if (Utils.file.exists() && Utils.file.length() <= 2) {
			System.out.println("no employees found");
			return;
		}

		System.out.print("Enter the id to revoke role: ");
		String id = sc.next().toUpperCase();

		Employee ems = dao.findById(id);
		System.out.println("Available roles: " + ems.getRole());
		if (!dao.checkExists(id)) {
			System.out.println("Employee with id:" + id + " does not exist");
			return;
		}

		boolean valid = false;
		String role = null;
		while (!valid) {
			System.out.print("Enter valid role: ");
			role = sc.next().toUpperCase();
			valid = ems.setRole(role);

			if (valid) {
				dao.revokeRole(id, role);
			} else {
				System.out.println("Invalid role re-enter again");
			}
		}
	}
}
