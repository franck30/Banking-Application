package com.franck.example.services.impl;

import com.franck.example.dto.AccountDto;
import com.franck.example.dto.UserDto;
import com.franck.example.exceptions.OperationNonPermittedException;
import com.franck.example.models.Account;
import com.franck.example.repository.AccountRepository;
import com.franck.example.services.AccountService;
import com.franck.example.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public Integer save(AccountDto dto) {
        // block account update -> iban cannot be updated

        if (dto.getId() != null) {
            throw new OperationNonPermittedException(
                    "Account cannot be updated",
                    "save account",
                    "Account",
                    "update not permitted"
            );
        }


        validator.validate(dto);



        Account account = AccountDto.toEntity(dto);

        boolean userhasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();

        if (userhasAlreadyAnAccount) {
            throw new OperationNonPermittedException(
                    "the selected user has already an active account",
                    "create account",
                    "Account service",
                    "Account creation"
            );
        }


        //generate random iban when creating new account else do not update the iban
        if (dto.getId() == null) {
            account.setIban(generateRandomIban());
        }
        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());



    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Account was found with the id : " + id));
    }

    @Override
    public void delete(Integer id) {
        //todo check delete account
        repository.deleteById(id);

    }

    public String generateRandomIban() {
        // generate an iban
        String iban = Iban.random(CountryCode.FR).toFormattedString();

        // check if the iban already exists
        boolean ibanExists = repository.findByIban(iban)
                .isPresent();



        // if exists -> generate new random iban
        if (ibanExists) {
            generateRandomIban();
        }

        // in no exists -> return generated iban

        return iban;
    }
}
