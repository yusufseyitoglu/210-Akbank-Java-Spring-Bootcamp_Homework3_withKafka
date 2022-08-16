package com.example.banking_system.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private String type;
	private int balance;
	private long accountNumber;
	private long lastModified;
	
	public String toCommaSeperatedFileFormat( ) {
		String formattedAccount = accountNumber + "," + name + "," + surname  + "," + email + "," + tc + "," + type;
		formattedAccount += "," + balance + "," + System.currentTimeMillis();
		return formattedAccount;
	}
}
