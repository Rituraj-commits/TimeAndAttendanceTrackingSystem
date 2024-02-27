package com.adp.timeattendance.dtos;

import com.adp.timeattendance.enums.Department;
import com.adp.timeattendance.enums.JobTitle;
import com.adp.timeattendance.enums.Role;
import com.adp.timeattendance.model.TimeShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer id;
    private TimeShift timeShift;
    private String name;
    private String email;
    private String phone;
    private JobTitle jobTitle;
    private Department department;
    private String token;
    private Role role;


}