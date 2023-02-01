package com.franck.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionSumDetails {

    LocalDateTime getTransactionDate();

    BigDecimal getAmount();

}
