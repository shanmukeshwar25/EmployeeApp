

package com.employees.services;

import java.util.Scanner;

import org.json.simple.parser.JSONParser;

import com.employees.dao.EmpDAO;
import com.employees.dao.EmpDAOImp;
import com.employees.model.Employee;

public class UpdatebyId {

	// updating or modifying details of specific ID
	public void updatebyid() {
		Scanner sc = new Scanner(System.in);
		boolean valid = true;
		String id=null;
		
		while(valid)
		{
		System.out.print("Enter Employee ID to update: ");
			id = sc.next();
			if (!Checkoper.checkExists(id)) {
				System.out.println("Employee with ID:" + id + " does not exists"+" enter the valid ID");
			}
			else {
				valid=false;
			}
		}
		sc.nextLine();
		JSONParser parser = new JSONParser();
		EmpDAO up = new EmpDAOImp();
		Employee e = new Employee();
		System.out.print("Enter the first name: ");
		String firstname = sc.next();
		System.out.print("Enter last name: ");
		String lastname = sc.next();
		String name= firstname+" "+lastname;
		e.setName(name);
		
		System.out.print("Enter the date of birth: ");
		int date  = sc.nextInt();
		
		System.out.print("Enter the month of birth: ");
		int month = sc.nextInt();
		
		System.out.print("Enter the year of birth: ");
		int year = sc.nextInt();
		String DOB = date+"-"+month+"-"+year;
		e.setDOB(DOB);
		
		System.out.print("Enter Address: ");
		String address = sc.next();
		e.setAddress(address);
		
		System.out.print("Enter email: ");
		String email = sc.next();
		e.setEmail(email);
		
		System.out.print("Enter Department : ");
		String depname = sc.next();
		e.setdepName(depname);
		
		up.updatebyId(e.getId(),e.getName(),e.getDOB(),e.getAddress(),e.getEmail(),e.getdepName());
		
	}
	public  void updateUserbyid() {
		try {
			String id = Checkoper.empid;
			if (!Checkoper.checkExists(id)) {
				System.out.println("Employee with ID:" + id + " does not exists");
				return;
			}
			Employee e = new Employee();
			Scanner sc = new Scanner(System.in);
			EmpDAO dao = new EmpDAOImp();
			System.out.print("Enter Address: ");
			String address = sc.next();
			e.setAddress(address);

			System.out.print("Enter email: ");
			String email = sc.next();
			e.setEmail(email);

			dao.updateUserbyId(id, address, email);
			dao.readData();
		} catch (NumberFormatException ex) {
			System.out.println("Please enter only numbers for ID, Age");
		}
	}
}
