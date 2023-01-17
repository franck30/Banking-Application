package com.franck.example.repository;

import com.franck.example.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {


    Optional<Account> findByIban(String iban);

    Optional<Account> findByUserId(Integer id);
}
