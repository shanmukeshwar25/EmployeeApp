

package com.employees.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.employees.model.Employee;
import com.employees.utils.Utils;

public class EmpDAOImp implements EmpDAO {

	
	private void printEmp(JSONObject emp) {
		System.out.println("ID:" + emp.get("id") + "|  Name: " + emp.get("name") + "  |  DOB: " + emp.get("dob")
				+ "  |  Address: " + emp.get("address") + "  |  Email: " + emp.get("email") + "  |  Role: " + emp.get("role")
				+ "  |  Department : " + emp.get("department"));
	}

	private void savetoFile(JSONArray arr) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utils.file))) {
			writer.write(arr.toJSONString());
			writer.newLine();
			writer.close();
		}

	}

	public void putdata(String id, String name, String pass, String dob, String address, String email,
			List<String> role, String depname) {
		JSONArray arr = new JSONArray();
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			jsonObject.put("name", name);
			jsonObject.put("password", pass);
			jsonObject.put("dob", dob);
			jsonObject.put("address", address);
			jsonObject.put("email", email);
			jsonObject.put("role", role);
			jsonObject.put("department", depname);
			if (Utils.file.exists() && Utils.file.length() > 0) {
				Object obj = parser.parse(new FileReader(Utils.file));
				arr = (JSONArray) obj;
			}
			arr.add(jsonObject);
			savetoFile(arr);
			System.out.println("Employee added succesfully");
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void deleteId(String id) {
		JSONParser parser = new JSONParser();
		try {
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			int ind = -1;
			JSONArray arr = (JSONArray) emps;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject emp = (JSONObject) arr.get(i);
				if (emp.get("id").equals(id)) {
					ind = i;
					System.out.println("Employee with ID:" + id + " deleted sucessfully");
					break;
				}
			}
			if (ind != -1) {
				arr.remove(ind);
			}
			savetoFile(arr);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void viewEmp() {
		JSONParser jsonParser = new JSONParser();
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("        EMPLOYEE DETAILS ");
		System.out.println("  ----------------------------");
		System.out.println();
		try {
			Object emps = jsonParser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject emp = (JSONObject) o;
				printEmp(emp);
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void viewEmpById(String id) {
		JSONParser parser = new JSONParser();
		try {
			boolean present = false;
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject emp = (JSONObject) o;
				if (emp.get("id").equals(id)) {
					present = true;
					printEmp(emp);
				}
			}
			if (!present) {
				System.out.println("no employee found");
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void updatebyId(String id, String name, String DOB, String address, String email,
			String depname) {
		JSONParser parser = new JSONParser();
		try {
			boolean present = false;
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject jsonObject = (JSONObject) o;
				if (jsonObject.get("id").equals(id)) {
					present = true;
					jsonObject.put("id", id);
					jsonObject.put("name", name);
					jsonObject.put("dob", DOB);
					jsonObject.put("address", address);
					jsonObject.put("email", email);
					jsonObject.put("department", depname);
				}
			}
			savetoFile(arr);

			if (!present) {
				System.out.println("no employee found in the file");
			}
		} catch (Exception e) {
			System.out.println("Error");
		}

	}

	public void setPass(String id, String password) {
		JSONParser parser = new JSONParser();
		try {
			boolean present = false;
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject jsonObject = (JSONObject) o;
				if (jsonObject.get("id").equals(id)) {
					present = true;
					jsonObject.put("password", password);
					System.out.println("Sucessfully updated password");
				}
				savetoFile(arr);
			}

			if (!present) {
				System.out.println("no employee found in the file");
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void updateUserbyId(String id, String address, String email) {
		JSONParser parser = new JSONParser();
		try {
			boolean present = false;
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			for (Object o : arr) {
				JSONObject jsonObject = (JSONObject) o;
				if (jsonObject.get("id").equals(id)) {
					present = true;
					jsonObject.put("address", address);
					jsonObject.put("email", email);
				}
				savetoFile(arr);
			}

			if (!present) {
				System.out.println("no employee found in the file");
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void grantRole(String id, String role) {
		JSONParser parser = new JSONParser();
		try {
			Object empdata = parser.parse(new FileReader(Utils.file));
			JSONArray arr = (JSONArray) empdata;
			for (Object obj : arr) {
				JSONObject jsonObject = (JSONObject) obj;
				String currId = (String) jsonObject.get("id");
				if (currId.equals(id)) {
					List<String> roleArray = (List<String>) jsonObject.get("role");
					if (!roleArray.contains(role)) {
						roleArray.add(role);
						System.out.println("Employee Updated role");
					} else {
						System.out.println("Cannot assign same role again");
					}
				}
			}
			savetoFile(arr);

		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void revokeRole(String id, String role) {
		JSONParser parser = new JSONParser();
		try {
			Object empdata = parser.parse(new FileReader(Utils.file));
			JSONArray arr = (JSONArray) empdata;
			for (Object obj : arr) {
				JSONObject jsonObject = (JSONObject) obj;
				String currId = (String) jsonObject.get("id");
				if (currId.equals(id)) {
					List<String> roleArray = (List<String>) jsonObject.get("role");
					if (roleArray.contains(role)) {
						roleArray.remove(role);
						System.out.println("Employee Updated role");
					} else {
						System.out.println("does not contain that role ");
					}
				}
			}
			savetoFile(arr);

		} catch (Exception e) {
			System.out.println("Error");
		}
	}
}


