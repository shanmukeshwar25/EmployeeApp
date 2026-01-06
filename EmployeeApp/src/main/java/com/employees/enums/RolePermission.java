
package com.employees.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RolePermission {
   private static final Map<String,Set<Operations>> map = new HashMap<>();
   
   public  RolePermission() {
	   map.put("ADMIN", EnumSet.of(
			   Operations.INSERT,
			   Operations.DELETE,
			   Operations.UPDATE,
			   Operations.VIEW,
			   Operations.VIEWBYID,
			   Operations.GRANT_ROLE,
			   Operations.REVOKE_ROLE,
			   Operations.RESET_PASSWORD,
			   Operations.EXIT
			   ));
	   
	   map.put("MANAGER", EnumSet.of(
			   Operations.UPDATE,
			   Operations.VIEW,
			   Operations.VIEWBYID,
			   Operations.EXIT
			   ));
	   map.put("USER", EnumSet.of(
			   Operations.VIEWBYID,
			   Operations.CHANGE_PASS,
			   Operations.EXIT,
			   Operations.UPDATE));
   }
   public  boolean hasAccess(String role,Operations operation) {
 		return map.get(role).contains(operation);	
  }
}

