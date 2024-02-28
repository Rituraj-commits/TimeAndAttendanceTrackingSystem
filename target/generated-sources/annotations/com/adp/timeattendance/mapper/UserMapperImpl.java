package com.adp.timeattendance.mapper;

import com.adp.timeattendance.dtos.SignUptDto;
import com.adp.timeattendance.dtos.UserDto;
import com.adp.timeattendance.model.Employee;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-28T11:09:31+0530",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( employee.getId() );
        userDto.timeShift( employee.getTimeShift() );
        userDto.name( employee.getName() );
        userDto.email( employee.getEmail() );
        userDto.phone( employee.getPhone() );
        userDto.jobTitle( employee.getJobTitle() );
        userDto.department( employee.getDepartment() );
        userDto.role( employee.getRole() );

        return userDto.build();
    }

    @Override
    public Employee signUpToUser(SignUptDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setName( signUpDto.getName() );
        employee.setRole( signUpDto.getRole() );
        employee.setTimeShift( signUpDto.getTimeShift() );
        employee.setEmail( signUpDto.getEmail() );
        employee.setPhone( signUpDto.getPhone() );
        employee.setJobTitle( signUpDto.getJobTitle() );
        employee.setDepartment( signUpDto.getDepartment() );

        return employee;
    }
}
