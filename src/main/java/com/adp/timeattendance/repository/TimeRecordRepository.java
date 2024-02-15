package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {
        List<TimeRecord> findbyEmployeeId(Integer employeeId);

}
