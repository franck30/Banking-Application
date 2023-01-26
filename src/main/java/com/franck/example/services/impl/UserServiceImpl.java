package com.franck.example.services.impl;


import com.franck.example.dto.AccountDto;
import com.franck.example.dto.UserDto;
import com.franck.example.models.Account;
import com.franck.example.models.User;
import com.franck.example.repository.AccountRepository;
import com.franck.example.repository.UserRepository;
import com.franck.example.services.AccountService;
import com.franck.example.services.UserService;
import com.franck.example.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ObjectsValidator<UserDto> validator;
    private final AccountService accountService;


    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        User savedUser = repository.save(user);
        return savedUser.getId() ;
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the id : " + id));

    }

    @Override
    public void delete(Integer id) {
        //todo check before delete

        repository.deleteById(id);
    }

    @Override
    public Integer validateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(true);
        //create a bank account for this user
        AccountDto accountDto = AccountDto.builder()
                .user(UserDto.fromEntity(user))
                .build();
        accountService.save(accountDto);
        repository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(false);
        repository.save(user);

        return user.getId();

    }
}
