

package com.employees.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.employees.utils.Utils;

public class AutoID {
	static int count = 0;
	public static int autoId() {
		JSONParser parser = new JSONParser();
		try {
			Object emps = parser.parse(new BufferedReader(new FileReader(Utils.file)));
			JSONArray arr = (JSONArray) emps;
			if (arr.size() == 0) {
				count++;
			} else {
				JSONObject obj = (JSONObject) (arr.get(arr.size() - 1));
				String s = (String) obj.get("id");
				if(s!=null && s.startsWith("EMP") && s.length()>3) {
					int no = Integer.parseInt(s.substring(3));
					count=no+1;
				}
				else {
					count=1;
				}
			}
			return count;
		}
		catch (IOException e) {
			System.out.println("error");
			
		} catch (ParseException e) {
			System.out.println("parsing falied");
		}
		return count;
	}
}

