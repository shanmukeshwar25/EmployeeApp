package com.employees.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.employees.utils.DatabaseConnecttion;

public class JdbcEmpDAOImp implements EmpDAO {
	public static Connection conn = DatabaseConnecttion.con;
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public void addEmp(String name, String pass, String dob, String address, String email, List<String> role,
			String depname) {
		try {
			LocalDate localDate = LocalDate.parse(dob, formatter);
			String insert_record = "insert into employees (emp_name, emp_dob, emp_address, emp_email, department_name) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement insert_stat = conn.prepareStatement(insert_record, new String[] { "emp_id" });
			insert_stat.setString(1, name);
			insert_stat.setDate(2, java.sql.Date.valueOf(localDate));
			insert_stat.setString(3, address);
			insert_stat.setString(4, email);
			insert_stat.setString(5, depname);

			insert_stat.executeUpdate();
			System.out.println("employee table");

			String gen_id = "";
			ResultSet generatekey = insert_stat.getGeneratedKeys();
			if (generatekey.next()) {
				gen_id = generatekey.getString(1);
			}

			String insert_login = "insert into emp_login (emp_id, emp_password) VALUES (?, ?);";
			PreparedStatement login_stat = conn.prepareStatement(insert_login);
			login_stat.setString(1, gen_id);
			login_stat.setString(2, pass);

			login_stat.executeUpdate();

			String insert_role = "insert into emp_roles (emp_id, emp_role) VALUES (?, ?);";
			PreparedStatement role_stat = conn.prepareStatement(insert_role);
			role_stat.setString(1, gen_id);
			role_stat.setObject(2, role.get(role.size() - 1), java.sql.Types.OTHER);

			role_stat.executeUpdate();

			System.out.println("successfull");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean viewEmp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void viewEmpById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatebyId(String id, String name, String DOB, String address, String email, String depname) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPass(String id, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserbyId(String id, String address, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void grantRole(String id, String role) {
		// TODO Auto-generated method stub

	}

	@Override
	public void revokeRole(String id, String role) {
		// TODO Auto-generated method stub

	}

	public boolean checkLogin(String id, String p) {
	   String query ="SELECT e.emp_password, r.emp_role FROM emp_login e INNER JOIN emp_roles r ON e.emp_id = r.emp_id WHERE e.emp_id = ?";
	   try {
		   PreparedStatement check = conn.prepareStatement(query);
		   
		   check.setString(1, id);
           ResultSet rs = check.executeQuery(); 
           if (rs.next()) {
        	   String storedPasswordHash = rs.getString("emp_password");
        	   String role  = rs.getString("emp_role");
                   if (storedPasswordHash.equals(p)) {
                	   ServerSideValidation.role = role;
                       return true;
                   }
               }
           return false;
	   }
	   catch(SQLException e)
	    {
		   System.out.println(e.getMessage());
	   }
	   return false;
       }

	@Override
	public void addEmp(String id, String name, String pass, String dob, String address, String email, List<String> role,
			String depname) {
		// TODO Auto-generated method stub
		
	}
	}
