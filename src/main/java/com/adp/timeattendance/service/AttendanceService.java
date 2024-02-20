package com.adp.timeattendance.service;


import java.util.List;

import com.adp.timeattendance.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;


import com.adp.timeattendance.model.Attendance;

import com.adp.timeattendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeService employeeService;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceById(Integer id) {
        Employee employee = employeeService.read(id);
        if(employee!=null){
            return attendanceRepository.findByEmployeeId(employee);

        }
        return null;
    }


}
