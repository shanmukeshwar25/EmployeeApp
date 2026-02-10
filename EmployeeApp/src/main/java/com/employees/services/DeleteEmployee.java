
package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;

public class DeleteEmployee {

	// delete a all details of a specific ID
	public void deleteEmployeeById(EmpDAO dao) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Employee ID to delete: ");
		String id = sc.next().toUpperCase();
		if (!dao.checkExists(id)) {
			System.out.println("Employee with ID:" + id + " does not exists");
			return;
		}
		try {
			dao.deleteId(id);
			dao.viewEmployees();
		} catch (Exception e) {
			System.out.println("failed to delete employee");
		}
	}
}
