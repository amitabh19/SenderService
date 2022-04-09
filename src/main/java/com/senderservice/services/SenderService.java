/**
 * 
 */
package com.senderservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.senderservice.entities.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.senderservice.encrypt.EncryptObject;
import com.senderservice.repositories.TransactionRepositories;


/**
 * @author amitabh
 *
 */
@Service
public class SenderService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionRepositories tr;

	/*
	 * public SenderService(RestTemplate restTemplate) { this.restTemplate =
	 * restTemplate; }
	 */

	public ResponseEntity<Transaction> sendTrans(Transaction t) throws NullPointerException {

		if (t.getAccountNumber()==null || t.getAccountNumber().isEmpty() || t.getAccountFrom().isEmpty() || t.getAmount().isEmpty()
				|| t.getCurrency().isEmpty() || t.getType().isEmpty()) {
			throw new NullPointerException("no field should be empty");
		}

		t.setAccountNumber(EncryptObject.encrypt(t.getAccountNumber()));
		t.setAccountFrom(EncryptObject.encrypt(t.getAccountFrom()));
		t.setAmount(EncryptObject.encrypt(t.getAmount()));
		t.setCurrency(EncryptObject.encrypt(t.getCurrency()));
		t.setType(EncryptObject.encrypt(t.getType()));
		t = tr.save(t);
		System.out.println("Sender Obj" + t.toString());
		String receiverURL = "https://receiver-microservice.herokuapp.com/receiver/receiveTransaction";
		return restTemplate.postForEntity(receiverURL, t, Transaction.class);
	}


}
