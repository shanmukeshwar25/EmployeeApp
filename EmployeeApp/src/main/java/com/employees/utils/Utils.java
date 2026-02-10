
package com.employees.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static final File file = new File("output.json");
	private static final String EMP_ID_REGEX = "TEK[0-9]+";
	private static final String PREFIX = "EMP";
	
	public static boolean validId(String id) {
		return id != null && id.matches(EMP_ID_REGEX);
	}
	
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
	public static boolean validateDOB(String dob) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateOfBirth = LocalDate.parse(dob, formatter);

			// validate year range
			int year = dateOfBirth.getYear();
			if (year < 1990 || year > 2005) {
				System.out.println("Invalid DOB: year must be between 1990 and 2005");
				return false;
			}
			return true;
		} catch (DateTimeParseException e) {
			System.out.println("Invalid date format");
			return false;
		}
	}

	// validation for email
	public static boolean validateMail(String email) {
		Pattern emailPattern = Pattern.compile("[A-Za-z09.]+@[A-Za-z0-9]+\\.[A-za-z]{2,4}");
		Matcher matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			System.out.println("Invalid email id");
			return false;
		}
		return true;
	}

	// validation for password
	private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()]).{5,10}$";

	public static boolean validatePassword(String password) {
		Pattern passPattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = passPattern.matcher(password);
		return matcher.matches();
	}

	public static boolean validateAddress(String address) {
		return address != null && !address.isBlank();
	}

	public static boolean validateDepartment(String department) {
		return department != null && department.matches("[A-Za-z ]{2,30}");
	}

	public static boolean validateName(String name) {
		return name != null && !name.isBlank() && name.matches("[A-Za-z]+");
	}

}
