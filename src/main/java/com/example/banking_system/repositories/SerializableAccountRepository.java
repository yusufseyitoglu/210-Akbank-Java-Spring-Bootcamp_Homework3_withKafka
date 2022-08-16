package com.example.banking_system.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import com.example.banking_system.models.Account;

public class SerializableAccountRepository implements AccountRepository{
	
@Override
public Account create(String name, String surname, String email, String tc, String type) {
	//create random number
	long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
	Account a = new Account();
	a.setAccountNumber(number);
	a.setBalance(0);
	a.setEmail(email);
	a.setName(name);
	a.setSurname(surname);
	a.setTc(tc);
	a.setType(type);
	a.setLastModified(System.currentTimeMillis());
	
	File f = new File(BASE_PATH + System.getProperty("file.seperator") + number);
	FileOutputStream fis;
	try {
		fis = new FileOutputStream(f);

		ObjectOutputStream os = new ObjectOutputStream(fis);
		os.writeObject(a);
		os.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	return a;
	}
@Override
	public Account findByAccountNumber(long accountNumber) {
	File f = new File(BASE_PATH + "\\" + accountNumber);
	try {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
		Account a = (Account)is.readObject();
		is.close();
		return a;
	} catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	} catch (ClassNotFoundException e) { 
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
	}
	@Override
	public boolean transfer(int amount, long ownerAccountNumber, long transferAccountNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
		public Account update(Account account) {
			// TODO Auto-generated method stub
			return null;
		}
	
}
