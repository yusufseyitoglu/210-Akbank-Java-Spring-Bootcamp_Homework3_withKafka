package com.example.banking_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_system.models.Account;
import com.example.banking_system.repositories.AccountRepository;
import com.example.banking_system.requests.AccountCreateRequest;
import com.example.banking_system.requests.MoneyTransferRequest;
import com.example.banking_system.responses.AccountCreateInvalidTypeResponse;
import com.example.banking_system.responses.AccountCreateSuccessResponse;

@RestController
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private KafkaTemplate<String, String> producer;

	@PostMapping("/accounts")
	public ResponseEntity<?> create(@RequestBody AccountCreateRequest request) {
	
		Account createdAccount = this.accountRepository.create(
				request.getName(),
				request.getSurname(), 
				request.getEmail(), 
				request.getTc(), 
				request.getType()
				);	
		
		if(createdAccount == null) {
			AccountCreateInvalidTypeResponse resp = new AccountCreateInvalidTypeResponse();
			resp.setMessage("Invalid Account Type: " + request.getType());
			return ResponseEntity.badRequest().body(resp);
		} else {
			AccountCreateSuccessResponse resp = new AccountCreateSuccessResponse();
			resp.setAccountNumber(createdAccount.getAccountNumber());
			resp.setMessage("Account Created");
			return ResponseEntity.ok().body(resp);
		}
	}
	@GetMapping("/accounts/{accountNumber}")
	public ResponseEntity<?> detail(@PathVariable long accountNumber) {
		Account a = this.accountRepository.findByAccountNumber(accountNumber);	
		return ResponseEntity.ok().lastModified(a.getLastModified()).body(a);
	}
	
	@PostMapping("/accounts/{accountNumber}")
	public ResponseEntity<?> transfer(@PathVariable long accountNumber, @RequestBody MoneyTransferRequest request) {
		boolean result = this.accountRepository.transfer(request.getAmount(),accountNumber, request.getTransferredAccountNumber());
		if(result) {
			/*for kafka log messages*/
			String logMessage = accountNumber + " transfer amount:" + request.getAmount() + " ,transferred account:" + request.getTransferredAccountNumber();	
			producer.send("logs", logMessage);
			return ResponseEntity.ok().body("Transfer Successful");
		}
		return ResponseEntity.badRequest().body("Insufficient Balance");
	}
	
}
