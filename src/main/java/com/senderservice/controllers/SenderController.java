package com.senderservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.senderservice.entities.Transaction;
import com.senderservice.services.SenderService;


@RestController
@RequestMapping("/sender")
public class SenderController {

	@Autowired
	private SenderService senderService;

	@PostMapping(path = "/sendTransaction")
	public ResponseEntity<?> encryptTransactionObject(@RequestBody Transaction transaction) {
		try {
			return senderService.sendTrans(transaction);
		} catch (Exception e) {
			if (e.getLocalizedMessage().isEmpty()) {
				return new ResponseEntity<String>("wrong transaction object sent",
						new ResponseEntity<>(HttpStatus.BAD_REQUEST).getStatusCode());
			} else {
				return new ResponseEntity<String>(e.getLocalizedMessage(),
						new ResponseEntity<>(HttpStatus.BAD_REQUEST).getStatusCode());
			}
		}
	}
	
}
