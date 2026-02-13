
package com.employees.controller;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.enums.Operations;
import com.employees.enums.RolePermission;
import com.employees.model.Session;

// displaying the main menu for operations
public class Menu {
	public static void menu(Session session, EmpDAO dao) {
		Scanner sc = new Scanner(System.in);
		boolean enter = true;
		RolePermission rolePermission = new RolePermission();
		System.out.println("WELCOME TO EMPLOYEE MANAGEMENT");
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("         OPERATIONS ");
		System.out.println("  ----------------------------");
		System.out.println();
		EmployeeController controller = new EmployeeController();
		for (Operations perm : Operations.values()) {
			if (rolePermission.hasAccess(session.getRoles(), perm)) {
				System.out.println(perm);
			}
		}
		while (enter) {
			System.out.println();
			System.out.print("Enter which operation to perform: ");
			String input = sc.nextLine();
			boolean valid_operation = false;
			Operations choice = null;
			try {
				choice = Operations.valueOf(input.toUpperCase());
				valid_operation = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid operation ");
				continue;
			}
			if (!rolePermission.hasAccess(session.getRoles(), choice)) {
				System.out.println("Access denied");
				continue;
			}

			if (valid_operation && rolePermission.hasAccess(session.getRoles(), choice)) {
				if (choice == Operations.INSERT)
					controller.addEmployee(dao);
				else if (choice == Operations.DELETE)
					controller.deleteEmployeeById(dao);
				else if (choice == Operations.VIEW)
					controller.viewallEmployee(dao);
				else if (choice == Operations.VIEWBYID)
					controller.viewEmployeeById(dao, session);
				else if (choice == Operations.RESET_PASSWORD)
					controller.resetPassword(dao, session);
				else if (choice == Operations.GRANT_ROLE)
					controller.grantRole(dao);
				else if (choice == Operations.REVOKE_ROLE)
					controller.revokeRole(dao);
				else if (choice == Operations.CHANGE_PASS)
					controller.changePassword(dao, session);
				else if (choice == Operations.UPDATE && !session.getRoles().contains("USER"))
					controller.updateEmployeeById(dao);
				else if (choice == Operations.UPDATE && session.getRoles().contains("USER"))
					controller.updateUserbyid(dao, session);
				else if (choice == Operations.LOGOUT) {
					System.out.println("logged out successfully");
					Login.start(dao);
				} else if (choice == Operations.EXIT)
					enter = false;
			}

		}
	}
}
