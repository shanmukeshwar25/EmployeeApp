package com.employees.storage;

import java.sql.Connection;
import java.util.Scanner;

import com.employees.utils.DatabaseConnection;

public class StorageSelection {

	public static StorageType storageSelection() {
		Scanner sc = new Scanner(System.in);

			System.out.println("Available storage type: ");
			for (StorageType storagetype : StorageType.values()) {
				System.out.println(storagetype + " ");
			}
			System.out.print("Select the storage type: ");
			String type = sc.nextLine().trim().toUpperCase();
			StorageType selected;
			
			try {
				selected = StorageType.valueOf(type);
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid StorageType entered. Defaulting to FILE.");
				return StorageType.FILE;
			}
			
			if (selected == StorageType.FILE) {
	            System.out.println("Using file system");
	            return StorageType.FILE;
	        }

	        try (Connection conn = DatabaseConnection.startConnection()) {
	            System.out.println("Connected to PostgreSQL successfully");
	            return StorageType.POSTGRES;
	        } catch (Exception e) {
	            System.out.println("DB connection failed. Falling back to FILE");
	            return StorageType.FILE;
	        }
	    }
}
