package com.franck.example.services.impl;

import com.franck.example.dto.TransactionSumDetails;
import com.franck.example.models.TransactionType;
import com.franck.example.repository.TransactionRepository;
import com.franck.example.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StaticsServiceImpl implements StatisticsService {


    private final TransactionRepository repository;

    @Override
    public List<TransactionSumDetails> findSumTransactionByDate(LocalDate startDate, LocalDate endDate, Integer userId) {

        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(startDate, LocalTime.of(23, 59, 59));

        return repository.findSumTransactionsByDate(start, end, userId);
    }

    @Override
    public BigDecimal getAccountBalance(Integer userId) {
        return repository.findAccountBalance(userId);
    }

    @Override
    public BigDecimal highestTransfert(Integer userId) {
        return repository.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERT);
    }

    @Override
    public BigDecimal highestDeposit(Integer userId) {
        return repository.findHighestAmountByTransactionType(userId, TransactionType.DEPOSIT);
    }
}
