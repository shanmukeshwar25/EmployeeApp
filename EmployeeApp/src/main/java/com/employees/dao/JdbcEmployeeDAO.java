package com.employees.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.employees.model.Employee;
import com.employees.model.LoginResult;
import com.employees.utils.DatabaseConnection;
import com.employees.utils.Utils;

public class JdbcEmployeeDAO implements EmpDAO {

	private static final String checkEmp = "select emp_id from emp_login where emp_id=?";

	private static final String roleQuery = "select emp_role from emp_roles where emp_id=?";
	private static final String loginQuery = "select emp_password from emp_login where emp_id=?";

	private static final String updatePassword = "update emp_login set emp_password=? where emp_id=?";

	private static final String grantQuery = "insert into emp_roles (emp_id,emp_role) values (?,?::emp_status)";
	private static final String revokeQuery = "DELETE from emp_roles where emp_id = ? and emp_role = ?::emp_status";

	private static final String addEmpLogin = "insert into emp_login (emp_id, emp_password) VALUES (?, ?);";
	private static final String addEmpRole = "insert into emp_roles (emp_id, emp_role) VALUES (?, ?);";
	private static final String addEmp = "insert into employees (emp_name, emp_dob, emp_address, emp_email, department_name) VALUES (?, ?, ?, ?, ?)";

	private static final String deleteQuery = "delete from employees where emp_id = ?";

	private static final String updateQuery = "update employees set emp_name=?,emp_dob=?,department_name=?,emp_address=?,emp_email=? where emp_id=?";
	private static final String updateUserQuery = "update employees set emp_address=?,emp_email=? where emp_id=?";

	private static final String viewQuery = "select * from employees";
	private static final String viewByIdQuery = "select * from employees where emp_id=?";

	private static final String countRolesQuery = "select count(*) from emp_roles where emp_id=?";
	private static final String deleteEmpIfNoRole = "delete from employees where emp_id=?";

	private static final String findEmpQuery = "select e.emp_id, e.emp_name, e.emp_dob, e.emp_address, "
			+ "e.emp_email, e.department_name, r.emp_role " + "FROM employees e "
			+ "join emp_roles r ON e.emp_id = r.emp_id " + "WHERE e.emp_id = ?";

	// print the employee record
	private void printRecord(ResultSet rs) throws SQLException {
		while (rs.next()) {
			System.out.println("ID:" + rs.getString("emp_id") + "|  Name: " + rs.getString("emp_name") + "  |  DOB: "
					+ rs.getString("emp_dob") + "  |  Address: " + rs.getString("emp_address") + "  |  Email: "
					+ rs.getString("emp_email") + "  |   Department : " + rs.getString("department_name"));
		}
	}

	private java.sql.Date toSqlDate(String dob) {
		try {
			return java.sql.Date.valueOf(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		} catch (Exception e) {
			System.out.println("Error in formatting date " + e.getMessage());
		}
		return null;
	}

	private Connection getConnection() throws SQLException {
		try {
			Connection conn = DatabaseConnection.startConnection();
			conn.setAutoCommit(false);
			return conn;
		} catch (IOException e) {
			throw new SQLException("Failed to load DB config", e);
		}
	}

	private void commitTransaction(Connection conn) {
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error during commit : " + e.getMessage());
		}
	}

	private void rollbackTransaction(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error during rollback : " + e.getMessage());
		}

	}

	public boolean checkExists(String id) {
		try (Connection conn = getConnection(); PreparedStatement stat = conn.prepareStatement(checkEmp)) {
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

	public void addEmployee(String name, String pass, String dob, String address, String email, List<String> role,
			String depname) {
		Connection conn = null;
		try {
			conn = getConnection();

			try (PreparedStatement insert_stat = conn.prepareStatement(addEmp, new String[] { "emp_id" });
					PreparedStatement login_stat = conn.prepareStatement(addEmpLogin);
					PreparedStatement role_stat = conn.prepareStatement(addEmpRole);) {

				insert_stat.setString(1, name);
				insert_stat.setDate(2, toSqlDate(dob));
				insert_stat.setString(3, address);
				insert_stat.setString(4, email);
				insert_stat.setString(5, depname);

				insert_stat.executeUpdate();

				ResultSet generatekey = insert_stat.getGeneratedKeys();
				String gen_id = "";
				if (generatekey.next()) {
					gen_id = generatekey.getString(1);
				}

				login_stat.setString(1, gen_id);
				login_stat.setString(2, pass);

				login_stat.executeUpdate();

				role_stat.setString(1, gen_id);
				role_stat.setObject(2, role.get(role.size() - 1), java.sql.Types.OTHER);

				role_stat.executeUpdate();

				System.out.println("successfully inserted " + gen_id);

			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			e.printStackTrace();
		}
	}

	@Override
	public void deleteId(String id) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement stat = conn.prepareStatement(deleteQuery)) {
				stat.setString(1, id);
				stat.executeUpdate();
				System.out.println(id + " successfully deleted");
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			throw e;
		}
	}

	@Override
	public void viewEmployees() {
		System.out.println();
		System.out.println("  ----------------------------");
		System.out.println("        EMPLOYEES DETAILS ");
		System.out.println("  ----------------------------");
		System.out.println();

		try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
			ResultSet rs = stat.executeQuery(viewQuery);
			printRecord(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void viewEmployeeById(String id) {
		try (Connection conn = getConnection(); PreparedStatement stat = conn.prepareStatement(viewByIdQuery)) {
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			printRecord(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateById(String id, String name, String DOB, String address, String email, String depname) {
		if(id == null || id.isBlank()) return;
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement stat = conn.prepareStatement(updateQuery)) {
				stat.setString(1, name);
				stat.setDate(2, toSqlDate(DOB));
				stat.setString(3, depname);
				stat.setString(4, address);
				stat.setString(5, email);
				stat.setString(6, id);
				stat.executeUpdate();
				System.out.println("updated id: " + id + " successfully");
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			System.out.println("failed to update employee " + e.getMessage());
		}
	}

	@Override
	public void setPassword(String id, String password) {
		if(id == null || id.isBlank()) return;
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement stat = conn.prepareStatement(updatePassword)) {
				stat.setString(1, password);
				stat.setString(2, id);
				stat.executeUpdate();
				System.out.println("password updated");
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			System.out.println("failed ro set password " + e.getMessage());
		}
	}

	@Override
	public void updateUserById(String id, String address, String email) {
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement stat = conn.prepareStatement(updateUserQuery)) {
				stat.setString(1, address);
				stat.setString(2, email);
				stat.setString(3, id);
				stat.executeUpdate();
				System.out.println("updated id: " + id + " successfully");
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void grantRole(String id, String role) {
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement ps = conn.prepareStatement(grantQuery)) {
				ps.setString(1, id);
				ps.setString(2, role);
				ps.executeUpdate();
				System.out.println("role granted successfully");
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}

	}

	public void revokeRole(String id, String role) {
		Connection conn = null;
		try {
			conn = getConnection();
			try (PreparedStatement revokeStat = conn.prepareStatement(revokeQuery);
					PreparedStatement countStat = conn.prepareStatement(countRolesQuery);
					PreparedStatement deleteStat = conn.prepareStatement(deleteEmpIfNoRole)) {
				revokeStat.setString(1, id);
				revokeStat.setString(2, role);
				revokeStat.executeUpdate();
				System.out.println("role successfully revoked");
				countStat.setString(1, id);

				try (ResultSet rs = countStat.executeQuery()) {
					rs.next();
					int count = rs.getInt(1);
					if (count == 0) {
						deleteStat.setString(1, id);
						deleteStat.executeUpdate();
						System.out.println("No roles left hence employee deleted");
					} else {
						System.out.println("Role revoked only");
					}
				}
			}
			commitTransaction(conn);
		} catch (SQLException e) {
			if (conn != null)
				rollbackTransaction(conn);
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Employee> findById(String id) {
		 if (id == null || id.isBlank()) {
		        return Optional.empty();
		    }
		Employee emp = null;
		try (Connection conn = getConnection(); PreparedStatement stat = conn.prepareStatement(findEmpQuery)) {

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
				return Optional.of(emp);
			} else {
				System.out.println("no employee found");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return Optional.empty();
	}

	public LoginResult checkLogin(String id, String p) {
		List<String> roles = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement check = conn.prepareStatement(loginQuery)) {
			check.setString(1, id);
			try (ResultSet rs = check.executeQuery()) {
				if (rs.next()) {
					String password = rs.getString("emp_password");
					if (password.equals(Utils.hashPass(p))) {
						try (PreparedStatement ps_role = conn.prepareStatement(roleQuery)) {
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
