package com.employees.services;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employees.exception.DataAccessException;
import com.employees.exception.EmployeeNotFoundException;
import com.employees.exception.ServiceException;
import com.employees.exception.ValidationException;
import com.employees.dao.EmpDAO;
import com.employees.utils.Utils;


public class EmployeeServices {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServices.class);
	
	public void delete(EmpDAO dao,String id) throws SQLException {
		logger.info("delete employee request recieved for id {}",id);
		if (!Utils.validId(id)) {
			logger.warn("validation failed: invalid id for id {}",id);
			throw new ValidationException("invalid id");
		}
		try {
			dao.deleteId(id);
			dao.viewEmployees();
		logger.info("Employee deleted succesfully for employee id {}",id);
		}catch(DataAccessException e) {
			logger.error("Database error during delete employee with id {} ",id,e);
			throw new ServiceException("unable to delete employee:"+e.getMessage());
		}catch(EmployeeNotFoundException e) {
			throw e;
		}
	}
	
	
	
}
