package com.example.banking_system.requests;

import lombok.Data;

@Data
public class AccountCreateRequest {
	private String name;
	private String surname;
	private String email;
	private String tc;
	private String type;
}
