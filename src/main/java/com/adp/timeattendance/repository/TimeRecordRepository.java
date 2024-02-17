package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {
        List<TimeRecord> findbyEmployeeId(Integer employeeId);

        @Query(value = "SELECT e.id AS employeeId, e.name, e.department, e.jobTitle, COUNT(a.id) AS total_attendances, SUM(CASE WHEN a.clockEvent = 'CLOCK_IN' THEN 1 ELSE 0 END) AS total_clock_ins, SUM(CASE WHEN a.clockEvent = 'CLOCK_OUT' THEN 1 ELSE 0 END) AS total_clock_outs, SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS total_absences, SUM(a.overtimeHours) AS total_overtime_hours FROM Employee e LEFT JOIN TimeRecord a ON e.id = a.employeeId WHERE a.attendanceDate BETWEEN TO_DATE('2023-03-01', 'YYYY-MM-DD') AND TO_DATE('2023-03-31', 'YYYY MM-DD') GROUP BY e.id, e.name, e.department, e.jobTitle ORDER BY e.id", nativeQuery = true)
        List<TimeRecord> getAttendanceDetails(Integer employeeId);

}
