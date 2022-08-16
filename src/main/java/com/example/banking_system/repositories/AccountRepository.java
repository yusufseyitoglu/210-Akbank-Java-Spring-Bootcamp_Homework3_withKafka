package com.example.banking_system.repositories;

import com.example.banking_system.models.Account;

public interface AccountRepository {
	
	public static final String BASE_PATH = "C:\\Users\\Yusa\\Desktop\\accounts\\";

	public Account create(
			String name, 
			String surname, 
			String email, 
			String tc, 
			String type
			);
	
	public Account findByAccountNumber(long accountNumber);
	
	public Account update(Account account);
	
	public boolean transfer(int amount, long ownerAccountNumber, long transferAccountNumber);

}
