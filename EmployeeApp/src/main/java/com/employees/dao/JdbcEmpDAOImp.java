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

	private static final String checkEmp = "select emp_id from emp_login where emp_id=?";

	private static final String roleQuery = "select emp_role from emp_roles where emp_id=?";
	private static final String loginQuery = "select emp_password from emp_login where emp_id=?";

	private static final String updatePassword = "update emp_login set emp_password=? where emp_id=?";

	private static final String grantQuery = "insert into emp_roles (emp_id,emp_role) values (?,?::emp_status)";
	private static final String revokeQuery = "DELETE from emp_roles where emp_id = ? and emp_role=?";

	private static final String addEmpLogin = "insert into emp_login (emp_id, emp_password) VALUES (?, ?);";
	private static final String addEmpRole = "insert into emp_roles (emp_id, emp_role) VALUES (?, ?);";
	private static final String addEmp = "insert into employees (emp_name, emp_dob, emp_address, emp_email, department_name) VALUES (?, ?, ?, ?, ?)";

	private static final String deleteQuery = "delete from employees where emp_id = ?";

	private static final String updateQuery = "update employees set emp_name=?,emp_dob=?,department_name=?,emp_address=?,emp_email=? where emp_id=?";
	private static final String updateUserQuery = "update employees set emp_address=?,emp_email=? where emp_id=?";

	private static final String viewQuery = "select * from employees";

	private static final String viewByIdQuery = "select * from employees where emp_id=?";

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

	private Connection getConnection() {
		try {
			Connection conn = DatabaseConnecttion.startConnection();
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			System.out.println("error in making connection: " + e.getMessage());
		}
		return null;
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

	public void addEmp(String name, String pass, String dob, String address, String email, List<String> role,
			String depname) {
		Connection conn = getConnection();
		try {
			PreparedStatement insert_stat = conn.prepareStatement(addEmp, new String[] { "emp_id" });
			insert_stat.setString(1, name);
			insert_stat.setDate(2, toSqlDate(dob));
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

			PreparedStatement login_stat = conn.prepareStatement(addEmpLogin);
			login_stat.setString(1, gen_id);
			login_stat.setString(2, pass);

			login_stat.executeUpdate();

			PreparedStatement role_stat = conn.prepareStatement(addEmpRole);
			role_stat.setString(1, gen_id);
			role_stat.setObject(2, role.get(role.size() - 1), java.sql.Types.OTHER);

			role_stat.executeUpdate();
			commitTransaction(conn);

			System.out.println("successfull inserted " + gen_id);

		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteId(String id) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(deleteQuery)) {
			stat.setString(1, id);
			stat.executeUpdate();
			commitTransaction(conn);
			System.out.println(id + " successfully deleted");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean viewEmp() {
		Connection conn = getConnection();
		try (Statement stat = conn.createStatement()) {
			ResultSet rs = stat.executeQuery(viewQuery);
			printRecord(rs);
			commitTransaction(conn);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void viewEmpById(String id) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(viewByIdQuery)) {
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			printRecord(rs);
			commitTransaction(conn);
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updatebyId(String id, String name, String DOB, String address, String email, String depname) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(updateQuery)) {
			stat.setString(1, name);
			stat.setDate(2, toSqlDate(DOB));
			stat.setString(3, depname);
			stat.setString(4, address);
			stat.setString(5, email);
			stat.setString(6, id);
			stat.executeUpdate();
			commitTransaction(conn);
			System.out.println("updated id: " + id + " successfully");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void setPass(String id, String password) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(updatePassword)) {
			stat.setString(1, password);
			stat.setString(2, id);
			stat.executeUpdate();
			commitTransaction(conn);
			System.out.println("password updated");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateUserbyId(String id, String address, String email) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(updateUserQuery)) {
			stat.setString(1, address);
			stat.setString(2, email);
			stat.setString(3, id);
			stat.executeUpdate();
			commitTransaction(conn);
			System.out.println("updated id: " + id + " successfully");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void grantRole(String id, String role) {
		Connection conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(grantQuery)) {
			ps.setString(1, id);
			ps.setString(2, role);
			ps.executeUpdate();
			commitTransaction(conn);
			System.out.println("role granted successfully");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void revokeRole(String id, String role) {
		Connection conn = getConnection();
		try (PreparedStatement stat = conn.prepareStatement(revokeQuery)) {
			stat.setString(1, id);
			stat.setObject(2, role, java.sql.Types.OTHER);
			stat.executeUpdate();
			commitTransaction(conn);
			System.out.println("role successfully revoked");
		} catch (SQLException e) {
			rollbackTransaction(conn);
			System.out.println(e.getMessage());
		}

	}

	@Override
	public Employee findById(String id) {
		// TODO Auto-generated method stub
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
			} else {
				System.out.println("no employee found");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return emp;
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
