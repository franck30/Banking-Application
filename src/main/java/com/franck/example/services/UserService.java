package com.franck.example.services;

import com.franck.example.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {


    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);
}
