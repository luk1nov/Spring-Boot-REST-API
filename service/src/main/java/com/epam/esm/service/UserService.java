package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService extends BaseService<UserDto> {
    UserDto create(UserDto userDto);

    UserDto findByEmail(String email);
}
