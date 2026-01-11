package com.employees.model;

import java.util.List;

public class LoginResult {
	String id;
	boolean valid;
	List<String> role;
   public LoginResult(boolean valid,String id,List<String> role) {
	   this.id=id;
	   this.valid=valid;
	   this.role=role;
   }
   public List<String> getRole() {
	   return role;
   }
   public boolean getPass() {
	   return valid;
   }
   public String getId() {
	   return id;
   }
}
