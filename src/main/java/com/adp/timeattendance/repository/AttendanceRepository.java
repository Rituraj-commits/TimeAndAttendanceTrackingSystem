package com.adp.timeattendance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;


public interface AttendanceRepository extends JpaRepository<Attendance, Integer>  {
	List<Attendance> findByEmployeeId(Employee employeeId);
	List<Attendance> deleteByEmployeeId(Employee employeeId);
}
