package com.adp.timeattendance.dtos;

import com.adp.timeattendance.enums.Department;
import com.adp.timeattendance.enums.JobTitle;
import com.adp.timeattendance.enums.Role;

import com.adp.timeattendance.model.TimeShift;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUptDto {

    private String name;
    private String email;
    private String phone;

    private JobTitle jobTitle;

    private Department department;
    private TimeShift timeShift;

    private Role role;
    private char[] password;

}
