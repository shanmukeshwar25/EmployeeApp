
package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;
import com.employees.utils.Utils;

public class DeleteEmp {

	// delete a all details of a specific ID
	public void delete() {

		Scanner sc = new Scanner(System.in);
		EmpDAO del = new EmpDAOImp();

		System.out.print("Enter Employee ID to delete: ");
		String id = sc.next();
		if (!ServerSideValidation.checkExists(id)) {
			System.out.println("Employee with ID:" + id + " does not exists");
			return;
		}
		del.deleteId(id);
		del.viewEmp();
	}
}
