
package com.employees.utils;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.employees.dao.ReadEmpData;

public class EmployeeIdGenerator {

	// generating new ID
	public static int generateNextEmployeeId() throws IOException, ParseException {
		JSONArray arr = ReadEmpData.readEmployeeData();
		int max = 0;
		for (Object o : arr) {
			JSONObject obj = (JSONObject) o;
			String id = (String) obj.get("id");
			if (id == null)
				continue;

			if (!id.startsWith("EMP"))
				continue;
			String numberPart = id.substring(3);

			if (!numberPart.matches("\\d+"))
				continue;

			int num = Integer.parseInt(numberPart);
			max = Math.max(max, num);
		}
		return max + 1;
	}

}
