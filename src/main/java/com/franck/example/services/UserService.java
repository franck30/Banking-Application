package com.franck.example.services;

import com.franck.example.dto.AuthenticationRequest;
import com.franck.example.dto.AuthenticationResponse;
import com.franck.example.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {


    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
