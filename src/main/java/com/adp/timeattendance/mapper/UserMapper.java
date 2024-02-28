package com.adp.timeattendance.mapper;

import com.adp.timeattendance.dtos.SignUptDto;
import com.adp.timeattendance.dtos.UserDto;
import com.adp.timeattendance.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(Employee employee);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", source = "signUpDto.name")
   // @Mapping(target = "jobTitle",source = "signUpDto.jobTitle")
    //@Mapping(target = "department",source = "signUpDto.department")
   // @Mapping(target = "timeShift", source = "signUpDto.timeShift")
    Employee signUpToUser(SignUptDto signUpDto);

}