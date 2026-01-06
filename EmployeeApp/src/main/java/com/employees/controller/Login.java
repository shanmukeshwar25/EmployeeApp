package com.employees.controller;

import java.util.Scanner;

import com.employees.dao.ServerSideValidation;

public class Login {
	//login page for admin
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
			if(ServerSideValidation.checkLogin(n, p)) {
				correct=false;
				Menu.menu(ServerSideValidation.role);
			}
			else {
				System.out.println("\nEnter the correct login details");
			}
    	}
 
    }
}

