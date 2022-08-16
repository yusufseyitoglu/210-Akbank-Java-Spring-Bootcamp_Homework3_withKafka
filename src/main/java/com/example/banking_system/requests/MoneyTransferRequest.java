package com.example.banking_system.requests;

import lombok.Data;

@Data
public class MoneyTransferRequest {
	private int amount;
	private long transferredAccountNumber;

}
