
package com.employees.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.employees.enums.Roles;
import com.employees.utils.Utils;

public class ServerSideValidation {

	// reading the data from file 
	public static JSONArray readEmployeeData() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		try (BufferedReader br = new BufferedReader(new FileReader(Utils.file))) {
			return (JSONArray) jsonParser.parse(br);
		}
	}

	// generating new ID
		static int ID = 0;
		public static int autoId() {
			try {
				JSONArray arr = readEmployeeData();
				if (arr.size() == 0) {
					ID++;
				} else {
					JSONObject obj = (JSONObject) (arr.get(arr.size() - 1));
					String s = (String) obj.get("id");
					if(s!=null && s.startsWith("EMP") && s.length()>3) {
						int no = Integer.parseInt(s.substring(3));
						ID=no+1;
					}
					else {
						ID=1;
					}
				}
				return ID;
			}
			catch (IOException e) {
				System.out.println("error");
				
			} catch (ParseException e) {
				System.out.println("parsing falied");
			}
			return ID;
		}
}
