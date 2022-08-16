package com.example.banking_system.responses;

import lombok.Data;

@Data
public class AccountCreateSuccessResponse {
	private long accountNumber;
	private String message;
}
