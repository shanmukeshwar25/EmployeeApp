package com.employees.dao;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.employees.utils.Utils;

public class EmpDAOImp implements EmpDAO {

	// show the employee data
	private void printEmp(JSONObject emp) {
		System.out.println("ID:" + emp.get("id") + "|  Name: " + emp.get("name") + "  |  DOB: " + emp.get("dob")
				+ "  |  Address: " + emp.get("address") + "  |  Email: " + emp.get("email") + "  |  Role: " + emp.get("role")
				+ "  |  Department : " + emp.get("department"));
	}

	// write a JSONArray to file 
	private void savetoFile(JSONArray arr) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utils.file))) {
			writer.write(arr.toJSONString());
			writer.newLine();
			writer.close();
		}

	}

	// adding new employee tooutput.json file
	public void addEmp(String id, String name, String pass, String dob, String address, String email,
			List<String> role, String depname) {
		JSONArray arr = new JSONArray();
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
				arr = ServerSideValidation.readEmployeeData();
			}
			arr.add(jsonObject);
			savetoFile(arr);
			System.out.println("Employee added succesfully");
		} catch (FileNotFoundException e) {
			System.out.println("file is not found");
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file");
		} catch (ParseException e) {
			System.out.println("error parsing employee data");
		}
	}

	//deleting employee from output.json file
	public void deleteId(String id) {
		try {
			JSONArray arr = ServerSideValidation.readEmployeeData();
			int ind = -1;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject emp = (JSONObject) arr.get(i);
				if (emp.get("id").equals(id)) {
					ind = i;
					break;
				}
			}
			if (ind == -1) {
				  System.out.println("Employee with ID " + id + " not found");
		            return;
			}
			arr.remove(ind);
			savetoFile(arr);
			System.out.println("Employee with ID:" + id + " deleted sucessfully");
		} catch (FileNotFoundException e) {
			System.out.println("file is not found");
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file");
		} catch (ParseException e) {
			System.out.println("error parsing employee data");
		}
	}

	// show the employee details from output.json file
	public boolean viewEmp() {
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("        EMPLOYEE DETAILS ");
		System.out.println("  ----------------------------");
		System.out.println();
		try {
			JSONArray arr = ServerSideValidation.readEmployeeData();
			if(arr==null || arr.isEmpty()) {
				return false;
			}
			for (Object o : arr) {
				JSONObject emp = (JSONObject) o;
				printEmp(emp);
			}
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}
		return false;
	}

	public void viewEmpById(String id) {
		try {
			boolean present = false;
			JSONArray arr = ServerSideValidation.readEmployeeData();
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
		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		} 
	}

	// updating data of employee 
	public void updatebyId(String id, String name, String DOB, String address, String email,
			String depname) {
		try {
			boolean present = false;
			JSONArray arr = ServerSideValidation.readEmployeeData();
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
		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}

	}

	// assign new password by user
	public void setPass(String id, String password) {
		try {
			boolean present = false;
			JSONArray arr = ServerSideValidation.readEmployeeData();
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
				System.out.println("no employee found in the file ");
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}
	}

	//Updates the logged-in user's address and email
	public void updateUserbyId(String id, String address, String email) {
		try {
			boolean present = false;
			JSONArray arr = ServerSideValidation.readEmployeeData();
			for (Object o : arr) {
				JSONObject jsonObject = (JSONObject) o;
				if (jsonObject.get("id").equals(id)) {
					present = true;
					jsonObject.put("address", address);
					jsonObject.put("email", email);
					System.out.println("Employee details updated ");
				}
				savetoFile(arr);
			}

			if (!present) {
				System.out.println("no employee found in the file");
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}
	}

	// adding new role to employee
	public void grantRole(String id, String role) {
		try {
			JSONArray arr = ServerSideValidation.readEmployeeData();
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

		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}
	}

	// removing the role assigned
	public void revokeRole(String id, String role) {
		try {
			JSONArray arr = ServerSideValidation.readEmployeeData();
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

		} catch (FileNotFoundException e) {
			System.out.println("file is not found "+e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error occured while reading the file "+e.getMessage());
		} catch (ParseException e) {
			System.out.println("error parsing employee data "+e.getMessage());
		}
	}

	@Override
	public void addEmp(String name, String pass, String dob, String address, String email, List<String> role,
			String depname) {
		// TODO Auto-generated method stub
		
	}
	
	// validating the login credentials 
		public boolean checkLogin(String id, String p) {
			try {
				JSONArray arr = ServerSideValidation.readEmployeeData();
				for (Object o : arr) {
					JSONObject obj = (JSONObject) o;
					if (obj.get("id").equals(id)) {
						String hashp = Utils.hashPass(p);
						if (obj.get("password").equals(hashp)) {

							System.out.println("You are a valid User");
							System.out.println();

							List<String> priority = Arrays.asList("ADMIN", "MANAGER", "USER");
							List<String>  roles = (List<String>) obj.get("role");

							roles.sort((r1, r2) -> Integer.compare(priority.indexOf(r1.toUpperCase()),
									priority.indexOf(r2.toUpperCase())));

							Collections.sort(roles);

							ServerSideValidation.role = (String) roles.get(0);

							ServerSideValidation.empid = id;
							
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
}


