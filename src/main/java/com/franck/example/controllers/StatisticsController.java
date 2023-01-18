package com.franck.example.controllers;


import com.franck.example.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;


    @GetMapping("/sum-by-date/{user-id}")
    public ResponseEntity<Map<LocalDate, BigDecimal>> findSumTransactionByDate(@RequestParam("start-date") LocalDate startDate,
                                                                               @RequestParam("end-date") LocalDate endDate,
                                                                               @PathVariable("user-id") Integer userId){


        return ResponseEntity.ok(service.findSumTransactionByDate(startDate, endDate, userId));
    }


    @GetMapping("/account-balance/{user-id}")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable("user-id") Integer userId){

        return ResponseEntity.ok(service.getAccountBalance(userId));

    }


    @GetMapping("/highest-transfert/{user-id}")
    public ResponseEntity<BigDecimal> highestTransfert(@PathVariable("user-id") Integer userId){


        return ResponseEntity.ok(service.highestTransfert(userId));
    }

    @GetMapping("/highest-deposit/{user-id}")
    public ResponseEntity<BigDecimal> highestDeposit(@PathVariable("user-id") Integer userId){


        return ResponseEntity.ok(service.highestDeposit(userId));
    }

}
