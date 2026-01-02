
package com.employees.controller;

import java.util.Scanner;

import com.employees.enums.Operations;
import com.employees.enums.RolePermission;
import com.employees.services.AddEmp;
import com.employees.services.DeleteEmp;
import com.employees.services.Role_op;
import com.employees.services.Setpassword;
import com.employees.services.UpdatebyId;
import com.employees.services.ViewEmp;

// displaying the main menu for operations
public class Menu {
	public static void menu(String role) {
		Scanner sc = new Scanner(System.in);
		boolean enter = true;
		AddEmp add = new AddEmp();
		DeleteEmp del = new DeleteEmp();
		UpdatebyId up = new UpdatebyId();
		ViewEmp view = new ViewEmp();
		Setpassword password = new Setpassword();
		Role_op roleop = new Role_op();
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

			}

//			if(rolePermission.hasAccess(role, choice) && choice==Operations.INSERT) 
//				add.addEmployee();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.UPDATE) 
//				up.updatebyid();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.DELETE) 
//				del.delete();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.VIEW) 
//				view.viewallEmployee();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.VIEWBYID) 
//				view.viewEmployeeById();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.RESET_PASSWORD) 
//				password.reset_password();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.GRANT_ROLE) 
//				roleop.grantRole();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.REVOKE_ROLE) 
//				roleop.revokeRole();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.CHANGE_PASS) 
//				password.setPass();
//			if(rolePermission.hasAccess(role, choice) && choice==Operations.EXIT) 
//				enter = false;

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
//		switch (role) {
//
//		case "MANAGER":
//			System.out.println("WELCOME TO MANAGER MANAGEMENT");
//			while (enter) {
//				System.out.println("  ----------------------------");
//				System.out.println("         OPERATIONS ");
//				System.out.println("  ----------------------------");
//				System.out.println();
//				for (Managerenum c : Managerenum.values()) {
//					System.out.println(c);
//				}
//				Managerenum choice = null;
//				boolean check = true;
//				// validate the operation name
//				while (check) {
//					System.out.print("Enter which operation to perform: ");
//					String com = sc.next().toUpperCase();
//					try {
//						choice = Managerenum.valueOf(com);
//						check = false;
//					} catch (IllegalArgumentException e) {
//						System.out.println("Invalid choice enter the valid operation");
//					}
//				}
//				switch (choice) {
//				// updating a details of specific ID
//				case UPDATE:
//					up.updateUserbyid();
//					break;
//				// viewing all the details in JSON file
//				case VIEW:
//					view.viewallEmployee();
//					break;
//				// viewing details of specific ID in JSON file
//				case VIEWBYID:
//					view.viewEmployeeById();
//					break;
//				// terminate
//				case EXIT:
//					enter = false;
//					break;
//				}
//			}
//			break;
//
//		case "USER":
//			System.out.println("WELCOME TO USER MANAGEMENT");
//			while (enter) {
//
//				System.out.println("  ----------------------------");
//				System.out.println("         OPERATIONS ");
//				System.out.println("  ----------------------------");
//				System.out.println();
//				for (Userenum c : Userenum.values()) {
//					System.out.println(c);
//				}
//				Userenum choice = null;
//				boolean check = true;
//				// validate the operation name
//				while (check) {
//					System.out.print("Enter which operation to perform: ");
//					String com = sc.next().toUpperCase();
//					try {
//						choice = Userenum.valueOf(com);
//						check = false;
//					} catch (IllegalArgumentException e) {
//						System.out.println("Invalid choice enter the valid operation");
//					}
//				}
//				switch (choice) {
//				// updating a details of specific ID
//				case UPDATE:
//					up.updateUserbyid();
//					break;
//				// viewing details of specific ID in JSON file
//				case VIEW:
//					view.viewEmployeeById();
//					break;
//				// set new password
//				case CHANGE_PASS:
//					Setpassword.setPass();
//					Login.log();
//					break;
//				// terminate
//				case EXIT:
//					enter = false;
//					break;
//				}
//			}
//			break;
//
//		case "ADMIN":
//			System.out.println("WELCOME TO ADMIN MANAGEMENT");
//			// entering into operations
//			while (enter) {
//				System.out.println("  ----------------------------");
//				System.out.println("         OPERATIONS ");
//				System.out.println("  ----------------------------");
//				System.out.println();
//				for (Choice c : Choice.values()) {
//					System.out.println(c);
//				}
//				Choice choice = null;
//				boolean check = true;
//				// validate the operation name
//				while (check) {
//					System.out.print("Enter which operation to perform: ");
//					String com = sc.next().toUpperCase();
//					try {
//						choice = Choice.valueOf(com);
//						check = false;
//					} catch (IllegalArgumentException e) {
//						System.out.println("Invalid choice enter the valid operation");
//					}
//				}
//				switch (choice) {
//				// inserting data
//				case INSERT:
//					add.addEmployee();
//					view.viewallEmployee();
//					break;
//				// delete the data
//				case DELETE:
//					del.delete();
//					break;
//				// updating a details of specific ID
//				case UPDATE:
//					up.updatebyid();
//					view.viewallEmployee();
//					break;
//				// viewing all the details in JSON file
//				case VIEW:
//					view.viewallEmployee();
//					break;
//				// viewing details of specific ID in JSON file
//				case VIEWBYID:
//					view.viewEmployeeById();
//					break;
//				// reset the employee's password
//				case RESET_PASSWORD:
//					Setpassword.reset_password();
//					break;
//				// to give role to the employee
//				case GRANT_ROLE:
//					role_op.grantRole();
//					break;
//				// undo the role assigned
//				case REVOKE_ROLE:
//					role_op.revokeRole();
//					break;
//				case EXIT:
//					enter = false;
//					break;
//				}
//			}
//			break;
//		}



