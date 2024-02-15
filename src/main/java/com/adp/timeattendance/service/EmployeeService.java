package com.adp.timeattendance.service;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public List<Employee> read(){
        return employeeRepository.findAll();
    }

    public Employee read(Integer id)
    {
        Optional<Employee> temp = employeeRepository.findById(id);
        Employee e=null;
        if(temp.isPresent())
        {
            e=temp.get();
        }
        return e;
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

    public Employee delete(Integer id) {
        Employee temp = read(id);
        if(temp!=null)
        {
            employeeRepository.delete(temp);
        }
        return temp;
    }


}