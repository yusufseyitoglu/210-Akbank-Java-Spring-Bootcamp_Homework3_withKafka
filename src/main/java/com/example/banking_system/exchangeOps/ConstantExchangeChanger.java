package com.example.banking_system.exchangeOps;

import org.springframework.stereotype.Component;

@Component
public class ConstantExchangeChanger implements ExchangeChanger{
	@Override
	public int changeExchange(int amount, String accountTypeOne, String accountTypeTwo) {
		
		if(accountTypeOne.equals("TL") && accountTypeTwo.equals("USD")) {
			return amount / 17;
		}
		
		if(accountTypeOne.equals("TL") && accountTypeTwo.equals("GAU")) {
			return amount / 1000;	
		}
		
		if(accountTypeOne.equals("USD") && accountTypeTwo.equals("TL")) {
			return amount * 17;	
		}
		if(accountTypeOne.equals("USD") && accountTypeTwo.equals("GAU")) {
			int tl = 17 * amount;
			return tl / 1000;		
		}
		if(accountTypeOne.equals("GAU") && accountTypeTwo.equals("TL")) {
			return amount * 1000;	
		}
		if(accountTypeOne.equals("GAU") && accountTypeTwo.equals("USD")) {
			return amount * 1000 / 17;	/*Önce tl ye ardından tl'den dolara dönüşüm*/
		}
		return amount;
	}

 
}

