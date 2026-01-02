
package com.employees.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.employees.controller.Menu;
import com.employees.model.Employee;
import com.employees.services.Checkoper;

public class Utils {

	public static final File file= new File("output.json");

//    static {
//        try {
//            File jarFile = new File(
//                    Utils.class.getProtectionDomain()
//                            .getCodeSource()
//                            .getLocation()
//                            .toURI()
//            );
//
//            File jarDir = jarFile.getParentFile();
//            file = new File(jarDir, "output.json");
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to locate JSON file", e);
//        }
//    }
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

	public static void validateDOB(String dob, Employee emp) {
		String[] birth = dob.split("-");
		if (birth.length != 3) {
			System.out.println("Invalid date format");
		}
		int date = Integer.parseInt(birth[0]);
		int month = Integer.parseInt(birth[1]);
		int year = Integer.parseInt(birth[2]);
		if ((date > 31 || date < 1) || (month < 1 || month > 12) || (year > 2005 || year < 1990)) {
			System.out.println("Invalid DOB");
			System.out.println();
			Menu.menu(Checkoper.role);
		}
		emp.setDOB(dob);
	}

	public static void validateMail(String email, Employee emp) {
		Pattern emailPattern = Pattern.compile("[A-Za-z09.]+@[A-Za-z0-9]+\\.[A-za-z]{2,4}");
		Matcher matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			System.out.println("Invalid email id");
			Menu.menu(Checkoper.role);
		}
		emp.setEmail(email);
	}

}

