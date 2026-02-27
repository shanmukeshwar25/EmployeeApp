
package com.employees.enums;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
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
			   Operations.CHANGE_PASS,
			   Operations.FETCH_INACTIVE,
			   Operations.LOGOUT,
			   Operations.RESET_PASSWORD,
			   Operations.EXIT
			   ));
	   
	   map.put("MANAGER", EnumSet.of(
			   Operations.UPDATE,
			   Operations.VIEW,
			   Operations.CHANGE_PASS,
			   Operations.VIEWBYID,
			   Operations.LOGOUT,
			   Operations.EXIT
			   ));
	   map.put("USER", EnumSet.of(
			   Operations.VIEWBYID,
			   Operations.CHANGE_PASS,
			   Operations.LOGOUT,
			   Operations.EXIT,
			   Operations.UPDATE));
   }

   public  boolean hasAccess(List<String> roles,Operations operation) {
       for(String role : roles) {
    	   if(map.getOrDefault(role,Collections.emptySet()).contains(operation)) {
				return true;
			}
		}
		return false;
      }
}

