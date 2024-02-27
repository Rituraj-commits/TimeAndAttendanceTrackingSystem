package com.adp.timeattendance.service;


import com.adp.timeattendance.dtos.CredentialsDto;
import com.adp.timeattendance.dtos.SignUptDto;
import com.adp.timeattendance.dtos.UserDto;
import com.adp.timeattendance.enums.Role;
import com.adp.timeattendance.exceptions.AppException;
import com.adp.timeattendance.mapper.UserMapper;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceClass {

    private final EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        Employee user = employeeRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUptDto userDto) {
        Optional<Employee> optionalUser = employeeRepository.findByEmail(userDto.getEmail());
        System.out.println(optionalUser);
        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Employee user = userMapper.signUpToUser(userDto);
        user.setRole(userDto.getRole());
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        Employee savedUser = employeeService.create(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByEmail(String login) {
        Employee user = employeeRepository.findByEmail(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}