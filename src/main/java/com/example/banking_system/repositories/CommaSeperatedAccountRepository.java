package com.example.banking_system.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banking_system.exchangeOps.ExchangeChanger;
import com.example.banking_system.models.Account;

@Component
public class CommaSeperatedAccountRepository implements AccountRepository{

	@Autowired
	private ExchangeChanger changer;
	
	private File logFile = new File("logs.txt");
	
	@Override
	public Account create(String name, String surname, String email, String tc, String type) {

		/*if you create wrong type, working here*/
		if(type.equals("TL") || type.equals("USD") || type.equals("GAU")) {
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
			
			String fileFormat = a.toCommaSeperatedFileFormat();
			String fileName = BASE_PATH + "\\" + number + ".txt";

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
				bw.write(fileFormat);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return a;
		}
		return null;
	}
	
	@Override
	public Account update(Account account) {

			
			account.setLastModified(System.currentTimeMillis());
			
			String fileFormat = account.toCommaSeperatedFileFormat();
			String fileName = BASE_PATH + "\\" + account.getAccountNumber() + ".txt";
			
			File f = new File(fileName);
			f.delete();
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write(fileFormat);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return account;
			
	}
	
	@Override
	public Account findByAccountNumber(long accountNumber) {
		File f = new File(BASE_PATH + "\\" + accountNumber + ".txt");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String accountDetailString = reader.readLine();
			String[] parts = accountDetailString.split(",");
			Account a = new Account();
			a.setAccountNumber(Long.parseLong(parts[0]));
			a.setName(parts[1]);
			a.setSurname(parts[2]);
			a.setEmail(parts[3]);
			a.setTc(parts[4]);
			a.setType(parts[5]);
			a.setBalance(Integer.parseInt(parts[6]));
			a.setLastModified(Long.valueOf(parts[7]));
			reader.close();
			return a;
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean transfer(int amount, long ownerAccountNumber, long transferAccountNumber) {
		
		Account ownerAccount = this.findByAccountNumber(ownerAccountNumber);
		Account transferAccount = this.findByAccountNumber(transferAccountNumber);

		if(ownerAccount.getBalance() < amount) {
			return false;
		}
		
		int transferAccountAmount = amount;
		if(!ownerAccount.getType().equals(transferAccount.getType())) {
			transferAccountAmount = this.changer.changeExchange(
					amount, 
					ownerAccount.getType(), 
					transferAccount.getType());
		}
		transferAccount.setBalance(transferAccount.getBalance() + transferAccountAmount);
		ownerAccount.setBalance(ownerAccount.getBalance() - amount);
		this.update(ownerAccount);
		this.update(transferAccount);
		return true;
	}
	
    public Boolean saveLog(String logLine) throws IOException {
        FileWriter writter = new FileWriter(logFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writter);
        try {
            bufferedWriter.write(logLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            bufferedWriter.close();

        }
        return true;
    }
}
