package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(userMapper::entityToDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        if(Objects.isNull(id)){
            throw new InvalidDataProvidedException("id is null");
        }
        return userRepository.findById(id).map(userMapper::entityToDto).orElseThrow(EntityNotFoundException::new);
    }
}
