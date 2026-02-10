package com.employees.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.employees.utils.Utils;

public class ReadEmpData {
	public static JSONArray readEmployeeData() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		try (BufferedReader br = new BufferedReader(new FileReader(Utils.file))) {
			return (JSONArray) jsonParser.parse(br);
		}
	}
}
