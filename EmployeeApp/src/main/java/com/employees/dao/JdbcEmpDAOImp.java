package com.employees.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.employees.model.Employee;
import com.employees.model.LoginResult;
import com.employees.utils.DatabaseConnecttion;
import com.employees.utils.Utils;

public class JdbcEmpDAOImp implements EmpDAO {
	public static Connection conn = DatabaseConnecttion.con;
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public boolean checkExists(String id) {
		String query = "select emp_id from emp_login where emp_id=?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

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

			System.out.println("successfull inserted " + gen_id);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteId(String id) {
		// deleting employee from employee table
		String query = "DELETE from employees where emp_id = ?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, id);
			stat.executeUpdate();
			System.out.println(id + " successfully deleted");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean viewEmp() {
		String query = "SELECT e.emp_id, e.emp_name, e.emp_dob, e.emp_address, e.emp_email, e.department_name, r.emp_role "
				+ "FROM employees e " + "JOIN emp_roles r ON e.emp_id = r.emp_id";
		try (Statement stat = conn.createStatement()) {
			ResultSet rs = stat.executeQuery(query);
			while (rs.next()) {
				System.out.println("ID:" + rs.getString("emp_id") + "|  Name: " + rs.getString("emp_name")
						+ "  |  DOB: " + rs.getString("emp_dob") + "  |  Address: " + rs.getString("emp_address")
						+ "  |  Email: " + rs.getString("emp_email") + "  |  Role: " + rs.getString("emp_role")
						+ "  |  Department : " + rs.getString("department_name"));

			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void viewEmpById(String id) {
		String query = "select e.emp_id, e.emp_name, e.emp_dob, e.emp_address, e.emp_email, e.department_name, r.emp_role "
				+ "FROM employees e " + "JOIN emp_roles r ON e.emp_id = r.emp_id where e.emp_id=?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				System.out.println("ID:" + rs.getString("emp_id") + "|  Name: " + rs.getString("emp_name")
						+ "  |  DOB: " + rs.getString("emp_dob") + "  |  Address: " + rs.getString("emp_address")
						+ "  |  Email: " + rs.getString("emp_email") + "  |  Role: " + rs.getString("emp_role")
						+ "  |  Department : " + rs.getString("department_name"));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updatebyId(String id, String name, String DOB, String address, String email, String depname) {
		// TODO Auto-generated method stub
		LocalDate localDate = LocalDate.parse(DOB, formatter);
		String query = "update employees set emp_name=?,emp_dob=?,department_name=?,emp_address=?,emp_email=? where emp_id=?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, name);
			stat.setDate(2, java.sql.Date.valueOf(localDate));
			stat.setString(3, depname);
			stat.setString(4, address);
			stat.setString(5, email);
			stat.setString(6, id);
			stat.executeUpdate();
			System.out.println("updated id: " + id + " successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void setPass(String id, String password) {
		// TODO Auto-generated method stub
		String query_update = "update emp_login set emp_password=? where emp_id=?";
		try (PreparedStatement stat = conn.prepareStatement(query_update)) {
			stat.setString(1, password);
			stat.setString(2, id);
			stat.executeUpdate();
			System.out.println("password updated");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateUserbyId(String id, String address, String email) {
		// TODO Auto-generated method stub
		String query = "update employees set emp_address=?,emp_email=? where emp_id=?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, address);
			stat.setString(2, email);
			stat.setString(3, id);
			stat.executeUpdate();
			System.out.println("updated id: " + id + " successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void grantRole(String id, String role) {
		// TODO Auto-generated method stub
		String query = "insert into emp_roles (emp_id,emp_role) values (?,?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			ps.setObject(2, role, java.sql.Types.OTHER);
			ps.executeUpdate();
			System.out.println("role granted successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void revokeRole(String id, String role) {
		// TODO Auto-generated method stub
		String query = "DELETE from emp_roles where emp_id = ? and emp_role=?";
		try (PreparedStatement stat = conn.prepareStatement(query)) {
			stat.setString(1, id);
			stat.setObject(2, role, java.sql.Types.OTHER);
			stat.executeUpdate();
			System.out.println("role successfully revoked");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public Employee findById(String id) {
		// TODO Auto-generated method stub
		String query = "SELECT e.emp_id, e.emp_name, e.emp_dob, e.emp_address, "
				+ "e.emp_email, e.department_name, r.emp_role " + "FROM employees e "
				+ "JOIN emp_roles r ON e.emp_id = r.emp_id " + "WHERE e.emp_id = ?";

		Employee emp = null;

		try (PreparedStatement stat = conn.prepareStatement(query)) {

			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();

			List<String> roles = new ArrayList<>();

			while (rs.next()) {
				if (emp == null) {
					emp = new Employee();
					emp.setId(rs.getString("emp_id"));
					emp.setName(rs.getString("emp_name"));
					emp.setdepName(rs.getString("department_name"));
					emp.setDOB(rs.getString("emp_dob"));
					emp.setAddress(rs.getString("emp_address"));
					emp.setEmail(rs.getString("emp_email"));
				}
				roles.add(rs.getString("emp_role"));
			}

			if (emp != null) {
				emp.setRoles(roles);
			} else {
				System.out.println("no employee found");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return emp;
	}

	public LoginResult checkLogin(String id, String p) {
		String query_role = "select emp_role from emp_roles where emp_id=?";
		String query_login = "select emp_password from emp_login where emp_id=?";
		List<String> roles = new ArrayList<>();
		try (PreparedStatement check = conn.prepareStatement(query_login)) {
			check.setString(1, id);
			try (ResultSet rs = check.executeQuery()) {
				if (rs.next()) {
					String password = rs.getString("emp_password");
					if (password.equals(Utils.hashPass(p))) {
						try (PreparedStatement ps_role = conn.prepareStatement(query_role)) {
							ps_role.setString(1, id);
							try (ResultSet rs_role = ps_role.executeQuery()) {
								while (rs_role.next()) {
									roles.add(rs_role.getString("emp_role"));
								}
							}
						}
						return new LoginResult(true, id, roles);
					} else {
						System.out.println("Invalid credential");
						return new LoginResult(false, null, null);
					}
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return new LoginResult(false, null, null);

	}
}
