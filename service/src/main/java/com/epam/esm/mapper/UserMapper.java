package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToUser(UserDto userDto);
    UserDto userToDto(User user);
}
