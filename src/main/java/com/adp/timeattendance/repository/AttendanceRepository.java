package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployeeId(Employee employeeId);

}
