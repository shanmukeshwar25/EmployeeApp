package com.employees.controller;

//login page for Admin
import java.util.Scanner;

import com.employees.services.LoginServices;
public class Login {
    public static void start() 
    {
    	Scanner sc = new Scanner(System.in);
    	boolean correct = true;
    	while (correct) {
			System.out.println("\n  -----------------------------");
			System.out.println("         LOGIN ");
			System.out.println("  -----------------------------");
			System.out.println();
			System.out.print("Enter the registered ID: ");
			String n = sc.next();
			System.out.print("Enter the registered password: ");
			String p = sc.next();
			if(LoginServices.checkLogin(n, p)) {
				correct=false;
				Menu.menu(LoginServices.role);
			}
			else {
				System.out.println("\nEnter the correct login details");
			}
    	}
 
    }
}

