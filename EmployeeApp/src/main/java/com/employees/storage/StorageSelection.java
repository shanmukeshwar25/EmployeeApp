package com.employees.storage;

import java.util.Scanner;

import com.employees.utils.DatabaseConnecttion;

public class StorageSelection {

	public static StorageType storageSelection() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Available storage type: ");
		for (StorageType storagetype : StorageType.values()) {
			System.out.print(storagetype + " ");
		}
		System.out.print("Select the storage type: ");
		String type = sc.next().toUpperCase();
		StorageType type1;
		try {
			type1 = StorageType.valueOf(type);	
		}
		catch (IllegalArgumentException e) {
            System.out.println("Invalid StorageType entered. Defaulting to FILE.");
            type1 = StorageType.FILE; 
        }
		 switch (type1) {
		case FILE:
			return StorageType.FILE;
		case POSTGRES:
			DatabaseConnecttion.startConnection();
			return StorageType.POSTGRES;
		}
		 return type1;
	}
}
