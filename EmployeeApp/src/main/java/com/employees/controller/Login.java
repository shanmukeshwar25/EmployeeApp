package com.employees.controller;


import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.employees.dao.EmpDAO;
import com.employees.model.LoginResult;
import com.employees.model.Session;

public class Login {
	//login page for admin
//	public static LoginResult result;
    public static void start(EmpDAO empdao) throws IOException, ParseException 
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
			LoginResult result = empdao.checkLogin(n, p);
			if(result.getValid()) {
				correct=false;
				Session session = new Session(result);
				Menu.menu(session,empdao);
			}
			else {
				System.out.println("\nEnter the correct login details");
			}
    	}
    }
}

