package com.franck.example.repository;

import com.franck.example.dto.TransactionDto;
import com.franck.example.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer > {

    List<Transaction> findAllByUserId(Integer userId);
}
