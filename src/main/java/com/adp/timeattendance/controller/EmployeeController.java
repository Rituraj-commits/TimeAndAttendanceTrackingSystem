package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> retrieveAllEmployee(){
        List<Employee> employees = employeeService.read();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
        Employee employee = employeeService.read(id);
        if(employee!=null) return ResponseEntity.ok(employee);
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee)
    {
    	Employee createdEmployee = employeeService.create(newEmployee);
    	return ResponseEntity.ok(createdEmployee);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee updatedEmployee){
        Employee employee = employeeService.update(updatedEmployee);
        if(employee!=null) return ResponseEntity.ok(employee);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Integer id){
        Employee deletedEmployee = employeeService.delete(id);
        if(deletedEmployee!=null) return ResponseEntity.ok(deletedEmployee);
        return ResponseEntity.notFound().build();
    }
}

