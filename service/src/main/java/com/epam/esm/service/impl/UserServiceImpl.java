package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final UserMapper userMapper = new UserMapperImpl();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(userMapper::userToDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        if(Objects.isNull(id)){
            throw new InvalidDataProvidedException("id is null");
        }
        return userRepository.findById(id).map(userMapper::userToDto).orElseThrow(EntityNotFoundException::new);
    }
}
