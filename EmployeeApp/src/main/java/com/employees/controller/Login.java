package com.employees.controller;


import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.ServerSideValidation;
import com.employees.model.LoginResult;

public class Login {
	//login page for admin
	public static LoginResult result;
    public static void start(EmpDAO empdao) 
    {
    	Scanner sc = new Scanner(System.in);
    	boolean correct = true;
    	while (correct) {
			System.out.println("\n  -----------------------------");
			System.out.println("         LOGIN ");
			System.out.println("  -----------------------------");
			System.out.println();
			System.out.print("Enter the registered ID: ");
			String n = sc.next().toUpperCase();
			System.out.print("Enter the registered password: ");
			String p = sc.next();
			result = empdao.checkLogin(n, p);
			if(result.getValid()) {
				correct=false;
				Menu.menu(result.getRole(),empdao);
			}
			else {
				System.out.println("\nEnter the correct login details");
			}
    	}
 
    }
}

