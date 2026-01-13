
package com.employees.controller;

import java.util.List;
import java.util.Scanner;

import com.employees.dao.EmpDAO;
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
	public static void menu(List<String> roles, EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		boolean enter = true;
		AddEmployee add = new AddEmployee();
		DeleteEmp del = new DeleteEmp();
		UpdatebyId up = new UpdatebyId();
		ViewEmp view = new ViewEmp();
		Setpassword password = new Setpassword();
		RoleManager roleop = new RoleManager();
		RolePermission rolePermission = new RolePermission();
		System.out.println("WELCOME TO EMPLOYEE MANAGEMENT");
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("         OPERATIONS ");
		System.out.println("  ----------------------------");
		System.out.println();

		for (Operations perm : Operations.values()) {
			if (rolePermission.hasAccess(roles, perm)) {
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

			if (valid_operation && rolePermission.hasAccess(roles, choice)) {
				if (choice == Operations.INSERT)
					add.addEmployee(dao);
				else if (choice == Operations.DELETE)
					del.delete(dao);
				else if (choice == Operations.VIEW)
					view.viewallEmployee(dao);
				else if (choice == Operations.VIEWBYID)
					view.viewEmployeeById(dao);
				else if (choice == Operations.RESET_PASSWORD)
					password.reset_password(dao);
				else if (choice == Operations.GRANT_ROLE)
					roleop.grantRole(dao);
				else if (choice == Operations.REVOKE_ROLE)
					roleop.revokeRole(dao);
				else if (choice == Operations.CHANGE_PASS)
					password.setPass(dao);
				else if (choice == Operations.UPDATE && !Login.result.getRole().contains("USER"))
					up.updatebyid(dao);
				else if (choice == Operations.UPDATE && Login.result.getRole().contains("USER"))
					up.updateUserbyid(dao);
				else if (choice == Operations.EXIT)
					enter = false;
			} else {
				System.out.println("Invalid operation enter again");
			}

		}
	}
}
