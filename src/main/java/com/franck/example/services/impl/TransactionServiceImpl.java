package com.franck.example.services.impl;

import com.franck.example.dto.TransactionDto;
import com.franck.example.models.Transaction;
import com.franck.example.models.TransactionType;
import com.franck.example.repository.TransactionRepository;
import com.franck.example.services.TransactionService;
import com.franck.example.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


    public final TransactionRepository repository;
    private final ObjectsValidator<TransactionDto> validator;

    @Override
    public Integer save(TransactionDto dto) {
        validator.validate(dto);
        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);

        return repository.save(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {
        return repository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No transaction was found with the id: " + id));
    }

    @Override
    public void delete(Integer id) {
        //todo check delete
        repository.deleteById(id);
    }

    private int getTransactionMultiplier(TransactionType type) {
        return TransactionType.TRANSFERT == type ? -1 : 1 ;
    }

    @Override
    public List<TransactionDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
