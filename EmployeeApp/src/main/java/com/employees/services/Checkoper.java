

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

public class Checkoper {
	public static String role;
	public static String empid;

//	@SuppressWarnings("unchecked")
	public static boolean checkLogin(String id, String p) {

		JSONParser jsonParser = new JSONParser();
		JSONArray arr = new JSONArray();
		try {
			Object emps = jsonParser.parse(new BufferedReader(new FileReader(Utils.file)));
			arr = (JSONArray) emps;
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

						Checkoper.role = (String) roles.get(0);

						empid = id;
						return true;
					}
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not found");
		} catch (IOException e) {
			System.out.println("error");
		} catch (ParseException e) {
			System.out.println("parsing falied");
		}
		return false;
	}

	// checking if that user exists or not
	public static boolean checkExists(String id) {
		JSONParser parser = new JSONParser();
		try {
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject emp = (JSONObject) o;
				if (emp.get("id").equals(id)) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			System.out.println("error");
		} catch (ParseException e) {
			System.out.println("parsing falied");
		}
		return false;
	}
}

