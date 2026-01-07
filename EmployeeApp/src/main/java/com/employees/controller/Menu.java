
package com.employees.controller;

import java.util.Scanner;

import com.employees.dao.ServerSideValidation;
import com.employees.enums.Operations;
import com.employees.enums.RolePermission;
import com.employees.services.AddEmployee;
import com.employees.services.DeleteEmp;
import com.employees.services.RoleManager;
import com.employees.services.Setpassword;
import com.employees.services.UpdatebyId;
import com.employees.services.ViewEmp;

// displaying the main menu for operations
public class Menu {
	public static void menu(String role) {
		Scanner sc = new Scanner(System.in);
		boolean enter = true;
		AddEmployee add = new AddEmployee();
		DeleteEmp del = new DeleteEmp();
		UpdatebyId up = new UpdatebyId();
		ViewEmp view = new ViewEmp();
		Setpassword password = new Setpassword();
		RoleManager roleop = new RoleManager();
		RolePermission rolePermission = new RolePermission();
		System.out.println("WELCOME TO " + role + " MANAGEMENT");
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("         OPERATIONS ");
		System.out.println("  ----------------------------");
		System.out.println();

		for (Operations perm : Operations.values()) {
			if (rolePermission.hasAccess(role, perm)) {
				System.out.println(perm);
			}
		}
		while (enter) {
			System.out.println();
			System.out.print("Enter which operation to perform: ");
			String input = sc.next();
			boolean valid_operation = false;
			Operations choice = null;
			try {
				choice = Operations.valueOf(input.toUpperCase());
				valid_operation = true;
			} catch (IllegalArgumentException e) {
                 System.out.println("Invalid operation ");
			}


			if (valid_operation && rolePermission.hasAccess(role, choice)) {
				if (choice == Operations.INSERT)
					add.addEmployee();
				else
				if (choice == Operations.DELETE)
					del.delete();
				else
				if (choice == Operations.VIEW)
					view.viewallEmployee();
				else
				if (choice == Operations.VIEWBYID)
					view.viewEmployeeById();
				else
				if (choice == Operations.RESET_PASSWORD)
					password.reset_password();
				else
				if (choice == Operations.GRANT_ROLE)
					roleop.grantRole();
				else
				if (choice == Operations.REVOKE_ROLE)
					roleop.revokeRole();
				else
				if (choice == Operations.CHANGE_PASS)
					password.setPass();
				else
				if (choice == Operations.EXIT)
					enter = false;
				else
				if (choice == Operations.UPDATE && !role.equals("USER")) 
					up.updatebyid();
				else
				if (choice == Operations.UPDATE && role.equals("USER")) 
					up.updateUserbyid();
			} else {
				System.out.println("Invalid operation enter again");
			}

		}
	}
}
