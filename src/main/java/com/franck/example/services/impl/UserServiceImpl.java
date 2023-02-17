package com.franck.example.services.impl;


import com.franck.example.config.JwtUtils;
import com.franck.example.dto.AccountDto;
import com.franck.example.dto.AuthenticationRequest;
import com.franck.example.dto.AuthenticationResponse;
import com.franck.example.dto.UserDto;
import com.franck.example.models.Account;
import com.franck.example.models.Role;
import com.franck.example.models.User;
import com.franck.example.repository.AccountRepository;
import com.franck.example.repository.RoleRepository;
import com.franck.example.repository.UserRepository;
import com.franck.example.services.AccountService;
import com.franck.example.services.UserService;
import com.franck.example.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final ObjectsValidator<UserDto> validator;
    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;


    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repository.save(user);
        return savedUser.getId();
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
    @Transactional
    public Integer validateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));
        //create a bank account for this user
        AccountDto accountDto = AccountDto.builder()
                .user(UserDto.fromEntity(user))
                .build();
        accountService.save(accountDto);

        user.setActive(true);
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

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(
                findOrCreateRole(ROLE_USER)
        );

        User savedUser = repository.save(user);

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());

        String token = jwtUtils.generateToken(savedUser, claims);


        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final User user = userRepository.findByEmail(request.getEmail()).get();

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());

        final String token = jwtUtils.generateToken(user, claims);

        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }


    private Role findOrCreateRole(String roleName) {

        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(Role.builder()
                    .name(roleName)
                    .build()
            );
        }

        return role;
    }
}
