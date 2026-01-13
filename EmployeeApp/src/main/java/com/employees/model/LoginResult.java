package com.employees.model;

import java.util.List;

public class LoginResult {
	String id;
	boolean valid;
	List<String> role;
    public LoginResult(boolean valid,String id,List<String> role) {
    	this.valid=valid;
	   this.id=id;
	   this.role=role;
   }
   public List<String> getRole() {
	   return role;
   }
   public boolean getValid() {
	   return valid;
   }
   public String getId() {
	   return id;
   }
}
