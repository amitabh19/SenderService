package com.senderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senderservice.entities.Transaction;

public interface TransactionRepositories extends JpaRepository<Transaction, String> {

}
