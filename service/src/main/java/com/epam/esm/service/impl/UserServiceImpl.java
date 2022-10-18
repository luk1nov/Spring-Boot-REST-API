package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(userMapper::entityToDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        if(Objects.isNull(id)){
            throw new InvalidDataProvidedException("Id is null");
        }
        return userRepository.findById(id).map(userMapper::entityToDto).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto create(UserDto userDto) {
        if(Objects.isNull(userDto)){
            throw new InvalidDataProvidedException("User dto is null");
        }
        configureUser(userDto);
        return userMapper.entityToDto(userRepository.save(userMapper.dtoToEntity(userDto)));
    }

    @Override
    public UserDto findByEmail(String email) {
        if(Objects.isNull(email)){
            throw new InvalidDataProvidedException("Email is null");
        }
        return userRepository.findByEmail(email).map(userMapper::entityToDto).orElseThrow(EntityNotFoundException::new);
    }

    private void configureUser(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(UserDto.Role.USER);
    }
}
