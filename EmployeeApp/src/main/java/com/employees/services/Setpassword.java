
package com.employees.services;

import java.util.Scanner;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.utils.Utils;

public class Setpassword {
   public  void setPass() {
		EmpDAO dao = new EmpDAOImp();
	    Scanner sc = new Scanner(System.in);
	    String id = Checkoper.empid;
		boolean checkpass=false;
		while(!checkpass)
		{
		System.out.print("Enter new password: ");
		String pass = sc.next();
		System.out.print("Re - Enter the new password: ");
		String repass = sc.next();
		if(pass.equals(repass))
		{
		String password = Utils.hashPass(pass);
		dao.setPass(id,password);
		checkpass=true;
		}
		else {
			System.out.println("Please re-enter the correct password ");
		}
		}
   }
   
   public  void reset_password() {
	   EmpDAO dao = new EmpDAOImp();
	   System.out.print("Enter the ID to view: ");
	   Scanner sc = new Scanner(System.in);
		try {
			String id = sc.next();
			String pass = "admin";
			String password = Utils.hashPass(pass);
			dao.setPass(id,password);
		}
		catch (NumberFormatException ex) {
			System.out.println("Please enter only numbers for ID, Age");
		}
   }
}
