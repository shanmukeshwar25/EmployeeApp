package com.employees.utils;

import java.util.UUID;
public class GeneratePassword {
  public static String generatePassword() {
	  return UUID.randomUUID().toString().substring(0,4);
  }
}
