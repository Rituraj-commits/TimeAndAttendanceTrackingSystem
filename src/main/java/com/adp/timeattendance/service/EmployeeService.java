package com.adp.timeattendance.service;

import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.repository.AttendanceRepository;
import com.adp.timeattendance.repository.EmployeeRepository;
import com.adp.timeattendance.repository.TimeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private TimeRecordRepository timeRecordRepository;

    public Employee create(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public List<Employee> read(){
        return employeeRepository.findAll();
    }

    public Employee read(Integer id)
    {

       return employeeRepository.findById(id).orElse(null);
    }

    public Employee update(Employee employee) {
        Employee temp = read(employee.getId());
        if(temp!=null)
        {
            temp=employee;
            employeeRepository.save(temp);
        }
        return temp;
    }


    public Employee delete(Integer id){
        Employee employee = read(id);
        {
            if(employee!=null){
                employeeRepository.delete(employee);
            }
        }
        return employee;
    }


}