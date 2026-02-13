package com.employees.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employees.exception.DataAccessException;
import com.employees.exception.EmployeeNotFoundException;
import com.employees.exception.ServiceException;
import com.employees.exception.ValidationException;
import com.employees.utils.EmployeeIdGenerator;
import com.employees.model.Employee;
import com.employees.model.Session;
import com.employees.dao.EmpDAO;
import com.employees.utils.GeneratePassword;
import com.employees.utils.Utils;

public class EmployeeServices {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServices.class);

	public String addEmployee(EmpDAO dao, String first, String last, String dob, String address, String email,
			List<String> roles, String dept) {

		String name = first + " " + last;

		logger.info("Add employee request received for {}", name);

		if (!Utils.validateName(first) || !Utils.validateName(last)) {
			logger.warn("invalid first name for name :  {}", name);
			throw new ValidationException("Invalid name");
		}

		if (!Utils.validateDOB(dob)) {
			logger.warn("invalid DOB for name :  {}", name);
			throw new ValidationException("Invalid DOB");
		}

		if (!Utils.validateMail(email)) {
			logger.warn("invalid mail for name :  {}", name);
			throw new ValidationException("Invalid email");
		}

		if (!Utils.validateDepartment(dept)) {
			logger.warn("invalid department for name :  {}", name);
			throw new ValidationException("Invalid department");
		}

		try {
			Employee emp = new Employee();

			String id = "EMP" + EmployeeIdGenerator.generateNextEmployeeId();
			emp.setId(id);
			emp.setName(name);
			emp.setDOB(dob);
			emp.setAddress(address);
			emp.setEmail(email);
			emp.setdepName(dept);
			emp.setRoles(roles);

			String password = "emp" + GeneratePassword.generatePassword();
			emp.setPass(Utils.hashPass(password));

			dao.addEmployee(name, Utils.hashPass(password), dob, address, email, roles, dept);
			dao.viewEmployees();
			logger.info("Employee saved successfully {}", id);
			return password;
		} catch (Exception e) {
			logger.error("Database failure", e);
			throw new ServiceException("Employee save failed");
		}

	}

	public void delete(EmpDAO dao, String id) {
		logger.info("delete employee request recieved for id {}", id);
		if (!Utils.validId(id)) {
			logger.warn("validation failed: invalid id for id {}", id);
			throw new ValidationException("invalid id");
		}
		try {
			dao.deleteId(id);
			dao.viewEmployees();
			logger.info("Employee deleted succesfully for employee id {}", id);
		} catch (DataAccessException e) {
			logger.error("Database error during delete employee with id {} ", id, e);
			throw new ServiceException("unable to delete employee:" + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewEmployeeById(EmpDAO dao, Session session) {
		String id = session.getId();
		logger.info("View employee by session id {}", id);
		if (!Utils.validId(id))
			throw new ValidationException("Invalid id");
		try {
			if (!dao.checkExists(id))
				throw new EmployeeNotFoundException("Employee not found");
			dao.viewEmployeeById(id);
			logger.info("View employee with id {} successful", id);
		} catch (Exception e) {
			logger.error("Failed fetching employee {}", id, e);
			throw new ServiceException("Unable to fetch employee");
		}
	}

	public void viewallEmployee(EmpDAO dao) {
		logger.info("View all employees request received");
		try {
			dao.viewEmployees();
			logger.info("View all employees successful");
		} catch (Exception e) {
			logger.error("Failed to fetch employees", e);
			throw new ServiceException("Unable to fetch employees");
		}
	}

	public void grantRole(EmpDAO dao, String id, String role) {
		logger.info("assign role {} request recieved for id {} ", role, id);
		if (!Utils.validId(id)) {
			logger.warn("validation failed: invalid id for id{}", id);
			throw new ValidationException("Invalid id");
		}
		if (!Utils.validateRole(role)) {
			logger.warn("validation failed: invalid role for id{}", role);
			throw new ValidationException("Invalid role");
		}
		try {
			dao.grantRole(id, role);
			logger.info("assign role {} succesfully to id {} ", role, id);
		} catch (DataAccessException e) {
			logger.error("Database error while assign the role for id {} ", id, e.getMessage());
			throw new ServiceException("unable to assign role:" + e);
		} catch (EmployeeNotFoundException | ValidationException e) {
			throw e;
		}
	}

	public void revokeRole(EmpDAO dao, String id, String role) {
		logger.info("revoke role {} request recieved for id {} ", role, id);
		if (!Utils.validId(id)) {
			logger.warn("validation failed: invalid id for id{}", id);
			throw new ValidationException("Invalid id");
		}
		if (!Utils.validateRole(role)) {
			logger.warn("validation failed: invalid role for id{}", id);
			throw new ValidationException("Invalid role");
		}
		try {
			dao.revokeRole(id, role);
			logger.info("revoke role {} succesfully to id {} ", role, id);
		} catch (DataAccessException e) {
			logger.error("Database error while revoke the role for id {} ", id, e);
			throw new ServiceException("unable to revoke role:" + e.getMessage());
		} catch (EmployeeNotFoundException | ValidationException e) {
			throw e;
		}
	}

	public String setPassword(EmpDAO dao, String id) {

		String newPass = "Emp@" + GeneratePassword.generatePassword();

		logger.info("Set password request for {}", id);

		if (!Utils.validId(id)) {
			logger.warn("validation failed: invalid id for id{}", id);
			throw new ValidationException("Invalid id");
		}

		if (!Utils.validatePassword(newPass)) {
			logger.warn("validation failed: invalid password for id{}", id);
			throw new ValidationException("Weak password");
		}

		try {
			String hashed = Utils.hashPass(newPass);
			dao.setPassword(id, hashed);
			return newPass;
		} catch (Exception e) {
			logger.error("Password set failed", e);
			throw new ServiceException("Unable to set password");
		}
	}

	public void changePassword(EmpDAO dao, String id, String pass, String repass) {
		logger.info("Reset password for {}", id);

		if (!Utils.validId(id))
			throw new ValidationException("Invalid id");
		if (!pass.equals(repass)) {
			logger.warn("validation failed: invalid new password for id{}", id);
			throw new ValidationException("Passwords do not match");
		}
		try {
			if (!dao.checkExists(id)) {
				throw new EmployeeNotFoundException("Employee not found");
			}
			dao.setPassword(id, Utils.hashPass(pass));
			logger.info("password reset {} succesfully to id {} ", id);
		} catch (Exception e) {
			throw new ServiceException("Password reset failed");
		}
	}

	public void updateEmployeeById(EmpDAO dao, String id, String firstname, String lastname, String dob, String address,
			String email, String dept) {

		if (!Utils.validId(id)) {
			logger.warn("invalid id");
			throw new ValidationException("Invalid id");
		}

		if (!Utils.validateName(firstname) || !Utils.validateName(lastname)) {
			logger.warn("invalid name for id :  {}", id);
			throw new ValidationException("Invalid name");
		}

		if (!Utils.validateDOB(dob)) {
			logger.warn("invalid DOB for id :  {}", id);
			throw new ValidationException("Invalid DOB");
		}

		if (!Utils.validateMail(email)) {
			logger.warn("invalid email for id :  {}", id);
			throw new ValidationException("Invalid email");
		}

		if (!Utils.validateAddress(address)) {
			logger.warn("invalid address for id :  {}", id);
			throw new ValidationException("Invalid address");
		}

		if (!Utils.validateDepartment(dept)) {
			logger.warn("invalid department for id :  {}", id);
			throw new ValidationException("Invalid department");
		}

		String fullName = firstname + " " + lastname;

		try {

			if (!dao.checkExists(id))
				throw new EmployeeNotFoundException("Employee not found");

			dao.updateById(id, fullName, dob, address, email, dept);

			logger.info("Employee updated successfully {}", id);

		} catch (EmployeeNotFoundException e) {
			throw e;

		} catch (Exception e) {
			logger.error("Update failed for {}", id, e);
			throw new ServiceException("Unable to update employee");
		}
	}

	public void updateUserbyid(EmpDAO dao, Session session, String address, String email) {

		String id = session.getId();

		logger.info("Self update request for {}", id);

		if (!Utils.validateAddress(address)) {
			logger.warn("invalid address for id :  {}", id);
			throw new ValidationException("Invalid address");
		}

		if (!Utils.validateMail(email)) {
			logger.warn("invalid email for id :  {}", id);
			throw new ValidationException("Invalid email");
		}

		try {

			if (!dao.checkExists(id))
				throw new EmployeeNotFoundException("Employee not found");

			dao.updateUserById(id, address, email);

			logger.info("Profile updated successfully {}", id);

		} catch (EmployeeNotFoundException e) {
			throw e;

		} catch (Exception e) {
			logger.error("Self update failed {}", id, e);
			throw new ServiceException("Unable to update profile");
		}
	}

	public void viewEmployeeById(EmpDAO dao, Session session, String id) {
		logger.info("View employee request for {}", id);

		if (!Utils.validId(id)) {
			logger.warn("invalid id");
			throw new ValidationException("Invalid id");
		}

		try {

			if (!dao.checkExists(id))
				throw new EmployeeNotFoundException("Employee not found");

			dao.viewEmployeeById(id);

		} catch (EmployeeNotFoundException e) {
			throw e;

		} catch (Exception e) {
			logger.error("View employee failed {}", id, e);
			throw new ServiceException("Unable to fetch employee");
		}
	}
}
