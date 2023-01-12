package com.franck.example.dto;


import com.franck.example.models.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountDto {

    private String iban;

    private UserDto user;

    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .iban(account.getIban())
                .user(UserDto.fromEntity(account.getUser()))
                .build();
    }


    public static Account toEntity(AccountDto account) {
        return Account.builder()
                .iban(account.getIban())
                .user(UserDto.toEntity(account.getUser()))
                .build();
    }
}
