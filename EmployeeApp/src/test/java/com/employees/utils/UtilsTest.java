package com.employees.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

public class UtilsTest {
	
	// DOB test cases 
	@Test
	public void testValidDOB() {
		assertTrue(Utils.validateDOB("10-10-2000"));
	}
	
	@Test
	public void testInvalidDOB() {
		assertFalse(Utils.validateDOB("10-10-2100"));
	}
	
	@Test
	public void testDOBLessThan1990() {
		assertFalse(Utils.validateDOB("10-10-1985"));
	}
	
	@Test
	public void testDOBGreaterThan2005(){
		assertFalse(Utils.validateDOB("10-10-2006"));
	}
	
	// Email test cases 
	@Test
	public void testValidEmail() {
		assertTrue(Utils.validateMail("ram@gmail.com"));
	}
	
	@Test
	public void testInvalidEmail() {
		assertFalse(Utils.validateMail("ram@.com"));
	}
	
	@Test
	public void testEmailWithoutExtension() {
		assertFalse(Utils.validateMail("ram@gmail"));
	}
	
	@Test
	public void testEmailWithoutSymbol() {
		assertFalse(Utils.validateMail("ram.gmail.com"));
	}

	
}
