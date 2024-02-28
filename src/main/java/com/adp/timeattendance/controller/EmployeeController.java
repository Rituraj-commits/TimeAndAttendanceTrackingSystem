package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.repository.AttendanceRepository;
import com.adp.timeattendance.repository.EmployeeRepository;
import com.adp.timeattendance.repository.TimeRecordRepository;
import com.adp.timeattendance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TimeRecordRepository timeRecordRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Employee>> retrieveAllEmployee() {
        List<Employee> employees = employeeService.read();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.read(id);

        if (employee != null) return ResponseEntity.ok(employee);
        return ResponseEntity.notFound().build();
    }


    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.id == #updatedEmployee.id")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.update(updatedEmployee);
        if (employee != null) return ResponseEntity.ok(employee);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee delete(@PathVariable Integer id) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            employeeService.delete(id);
        }
        return employee;
    }


//    @DeleteMapping("/{id}")
//    @Transactional
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public Employee delete(@PathVariable Integer id){
//        Employee employee = employeeService.read(id);
//        {
//            if(employee!=null)
//            {
//
//                // Delete TimeRecords and Attendances
//                timeRecordRepository.deleteByEmployeeId(employee);
//                attendanceRepository.deleteByEmployeeId(employee);
//
//                employeeRepository.delete(employee);
//            }
//        }
//        return employee;
//    }


}

