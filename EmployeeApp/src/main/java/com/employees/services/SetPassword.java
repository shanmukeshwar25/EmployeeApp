
package com.employees.services;

import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.model.Session;
import com.employees.utils.GeneratePassword;
import com.employees.utils.Utils;

public class SetPassword {

	// assign new password for user
	public void setPassword(EmpDAO dao,Session session) {
		Scanner sc = new Scanner(System.in);
		String id = session.getId();
		boolean checkpass = false;
		while (!checkpass) {
			System.out.print("Enter new password: (type 'exit' to cancel): ");
			String pass = sc.next();
			
			if (pass.equalsIgnoreCase("exit")) {
			    System.out.println("Password change cancelled");
			    return;
			}
			
			System.out.print("Re - Enter the new password: ");
			String repass = sc.next();
			if (pass.equals(repass)) {
				if (Utils.validatePassword(pass)) {
					String password = Utils.hashPass(pass);
					dao.setPassword(id, password);
					checkpass = true;
					System.out.println("Password updated successfully");
				} else {
					System.out.println("Invalid Password format");
				}
			} else {
				System.out.println("Please re-enter the correct password ");
			}
		}
	}

	// reset password for employee
	public void resetPassword(EmpDAO dao) {
		System.out.print("Enter the ID to view: ");
		Scanner sc = new Scanner(System.in);
		String id = sc.next().toUpperCase();
		if (!dao.checkExists(id)) {
			System.out.println("Employee with ID: " + id + " does not exist");
			return;
		}
		String password = "emp" + GeneratePassword.generatePassword();
		System.out.println("the default password is : " + password);
		dao.setPassword(id, Utils.hashPass(password));
		System.out.println("Password reset successful");
	}
}
