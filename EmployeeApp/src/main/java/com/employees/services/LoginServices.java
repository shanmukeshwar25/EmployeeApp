
package com.employees.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.employees.utils.Utils;

public class LoginServices {
	public static String role;
	public static String empid;

	// reading the data from file 
	public static JSONArray readEmployeeData() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		try (BufferedReader br = new BufferedReader(new FileReader(Utils.file))) {
			return (JSONArray) jsonParser.parse(br);
		}
	}
	
	// validating the login credentials 
	public static boolean checkLogin(String id, String p) {
		try {
			JSONArray arr = readEmployeeData();
			for (Object o : arr) {
				JSONObject obj = (JSONObject) o;
				if (obj.get("id").equals(id)) {
					String hashp = Utils.hashPass(p);
					if (obj.get("password").equals(hashp)) {

						System.out.println("You are a valid User");
						System.out.println();

						List<String> priority = Arrays.asList("ADMIN", "MANAGER", "USER");
						List<String> roles = (List<String>) obj.get("role");

						roles.sort((r1, r2) -> Integer.compare(priority.indexOf(r1.toUpperCase()),
								priority.indexOf(r2.toUpperCase())));

						Collections.sort(roles);

						LoginServices.role = (String) roles.get(0);

						empid = id;
						return true;
					} else {
						System.out.println("Incorrect credentails");
						return false;
					}
				}

			}
			System.out.println("Incorrect credentails");
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("file is not found");
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file");
		} catch (ParseException e) {
			System.out.println("error parsing employee data");
		}
		return false;
	}

	// checking if that user exists or not
	public static boolean checkExists(String id) {
		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = readEmployeeData();
			for (Object o : arr) {
				JSONObject emp = (JSONObject) o;
				if (id.equals(emp.get("id"))) {
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("file is not found");
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file");
		} catch (ParseException e) {
			System.out.println("error parsing employee data");
		}
		return false;
	}
}
