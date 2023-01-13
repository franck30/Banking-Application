package com.franck.example.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
public class Transaction extends AbstractEntity{




    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String destinationIban;



    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}