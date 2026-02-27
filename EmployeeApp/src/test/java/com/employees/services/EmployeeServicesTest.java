
package com.employees.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.employees.dao.EmpDAO;
import com.employees.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
class EmployeeServicesTest {

	@Mock
	private EmpDAO dao;

	@InjectMocks
	private EmployeeServices service;

	@Test
	void addEmployee_validEmployee_shouldCallDao() {

		List<String> roles = List.of("USER");

		service.addEmployee(dao, "ram", "prasad", "12-12-2003", "hyd", "ram@gmail.com", roles, "sales");

		verify(dao).addEmployee(eq("ram prasad"), anyString(), eq("12-12-2003"), eq("hyd"), eq("ram@gmail.com"),
				eq(roles), eq("sales"));
	}

	@Test
	void addEmployee_invalidEmail_shouldThrowValidationException() {

		List<String> roles = List.of("USER");

		ValidationException exception = assertThrows(ValidationException.class,
				() -> service.addEmployee(dao, "Ram", "Prasad", "12-12-2003", "hyd", "ramgmail.com", roles, "sales"));

		assertEquals("Invalid email", exception.getMessage());
	}

	@Test
	void grantRole_invalidRole_shouldThrowValidationException() {

		ValidationException exception = assertThrows(ValidationException.class,
				() -> service.grantRole(dao, "EMP1", "ADMIN123"));

		assertEquals("Invalid role", exception.getMessage());
	}

	@Test
	void delete_validEmployee_shouldCallDao() {

		service.delete(dao, "EMP3");
		verify(dao).deleteId("EMP3");
	}

}
