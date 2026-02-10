
package com.employees.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.employees.dao.EmpDAO;
import com.employees.enums.Operations;
import com.employees.enums.RolePermission;
import com.employees.model.Session;
import com.employees.services.AddEmployee;
import com.employees.services.DeleteEmployee;
import com.employees.services.RoleManager;
import com.employees.services.SetPassword;
import com.employees.services.UpdateEmployee;
import com.employees.services.ViewEmployee;

// displaying the main menu for operations
public class Menu {
	public static void menu(Session session, EmpDAO dao) throws IOException, ParseException {
		Scanner sc = new Scanner(System.in);
		boolean enter = true;
		AddEmployee add = new AddEmployee();
		DeleteEmployee del = new DeleteEmployee();
		UpdateEmployee up = new UpdateEmployee();
		ViewEmployee view = new ViewEmployee();
		SetPassword password = new SetPassword();
		RoleManager roleop = new RoleManager();
		RolePermission rolePermission = new RolePermission();
		System.out.println("WELCOME TO EMPLOYEE MANAGEMENT");
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("         OPERATIONS ");
		System.out.println("  ----------------------------");
		System.out.println();

		for (Operations perm : Operations.values()) {
			if (rolePermission.hasAccess(session.getRoles(), perm)) {
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

			if (valid_operation && rolePermission.hasAccess(session.getRoles(), choice)) {
				if (choice == Operations.INSERT)
					add.addEmployee(dao);
				else if (choice == Operations.DELETE)
					del.deleteEmployeeById(dao);
				else if (choice == Operations.VIEW)
					view.viewallEmployee(dao);
				else if (choice == Operations.VIEWBYID)
					view.viewEmployeeById(dao,session);
				else if (choice == Operations.RESET_PASSWORD)
					password.resetPassword(dao);
				else if (choice == Operations.GRANT_ROLE)
					roleop.grantRole(dao);
				else if (choice == Operations.REVOKE_ROLE)
					roleop.revokeRole(dao);
				else if (choice == Operations.CHANGE_PASS)
					password.setPassword(dao,session);
				else if (choice == Operations.UPDATE && !session.getRoles().contains("USER"))
					up.updateEmployeeById(dao);
				else if (choice == Operations.UPDATE && session.getRoles().contains("USER"))
					up.updateUserbyid(dao,session);
				else if (choice == Operations.EXIT)
					enter = false;
			} else {
				System.out.println("Invalid operation enter again");
			}

		}
	}
}
