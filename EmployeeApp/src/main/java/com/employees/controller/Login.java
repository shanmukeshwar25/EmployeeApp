package com.employees.controller;

//login page for Admin
import java.util.Scanner;

import com.employees.services.Checkoper;
public class Login {
    public static void log() 
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
			if(!Checkoper.checkLogin(n, p)) {
				System.out.println("\nEnter the correct login details");
			}
			else {
				correct=false;
				Menu.menu(Checkoper.role);
			}
    	}
 
    }
}

