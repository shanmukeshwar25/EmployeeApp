
package com.employees.services;

import java.util.Scanner;

import com.employees.controller.Login;
import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.dao.ServerSideValidation;
import com.employees.utils.GeneratePassword;
import com.employees.utils.Utils;

public class Setpassword {

	// assign new password for user
	public void setPass(EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		String id = Login.result.getId();
		boolean checkpass = false;
		while (!checkpass) {
			System.out.print("Enter new password: ");
			String pass = sc.next();
			System.out.print("Re - Enter the new password: ");
			String repass = sc.next();
			if (pass.equals(repass)) {
				if(Utils.validatePassword(pass)) {
					String password = Utils.hashPass(pass);
					dao.setPass(id, password);
					checkpass = true;
				}
				else {
					System.out.println("Invalid Password format");
				}
			} else {
				System.out.println("Please re-enter the correct password ");
			}
		}
	}

	// reset password for employee 
	public void reset_password(EmpDAO dao) {
		System.out.print("Enter the ID to view: ");
		Scanner sc = new Scanner(System.in);
		try {
			String id = sc.next().toUpperCase();
			String password = "emp"+GeneratePassword.generatePassword();
			System.out.println("the default password is : "+password);
			dao.setPass(id, Utils.hashPass(password));
		} catch (NumberFormatException ex) {
			System.out.println("Please enter only numbers for ID, Age");
		}
	}
}
