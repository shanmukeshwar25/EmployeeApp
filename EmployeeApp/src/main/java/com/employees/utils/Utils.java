
package com.employees.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.employees.controller.Menu;
import com.employees.dao.ServerSideValidation;
import com.employees.model.Employee;

public class Utils {

	public static final File file = new File("output.json");
	public static String pass = "admin";
	
	// hash passwords
	public static String hashPass(String pass) {

		String hashValue = null;

		try {

			MessageDigest md = MessageDigest.getInstance("SHA-224");
			byte[] digest = md.digest(pass.getBytes("UTF-8"));

			// Convert the byte array to a hexadecimal string
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < digest.length; i++) {
				// converts the byte to a hex string and ensures it's always 2 characters
				sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	// validating the date of birth 
	public static void validateDOB(String dob, Employee emp) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateOfBirth = LocalDate.parse(dob, formatter);

			// Optional: validate year range
			int year = dateOfBirth.getYear();
			if (year < 1990 || year > 2005) {
				System.out.println("Invalid DOB: year must be between 1990 and 2005");
				Menu.menu(ServerSideValidation.role);
				return;
			}
			emp.setDOB(dob);
		} catch (DateTimeParseException e) {
			System.out.println("Invalid date format");
			Menu.menu(ServerSideValidation.role);
		}
	}

	// validation for email
	public static void validateMail(String email, Employee emp) {
		Pattern emailPattern = Pattern.compile("[A-Za-z09.]+@[A-Za-z0-9]+\\.[A-za-z]{2,4}");
		Matcher matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			System.out.println("Invalid email id");
			Menu.menu(ServerSideValidation.role);
		}
		emp.setEmail(email);
	}

}
