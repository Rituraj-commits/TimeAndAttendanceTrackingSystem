package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {
    List<TimeRecord> findByEmployeeId(Employee employeeId);

    @Query(value = """
            SELECT e.ID, e.NAME, COUNT(a.LATEARRIVAL) AS LATEARRIVALS,
            SUM(a.OVERTIMEHOURS) AS TOTALOVERTIMEHOURS,
            COUNT(CASE WHEN a.STATUS = 'ABSENT' THEN 1 ELSE NULL END) AS TOTALABSENTS,
            COUNT(CASE WHEN a.STATUS = 'PRESENT' THEN 1 ELSE NULL END) AS TOTALPRESENTS
            FROM Employee_G5_Jan16 e LEFT JOIN Attendance_G5_Jan16 a ON e.ID = a.EMPID
            WHERE a.ATTENDANCEDATE BETWEEN TO_DATE('2024-02-01', 'YYYY-MM-DD') AND
            TO_DATE('2024-03-01', 'YYYY-MM-DD') GROUP BY e.ID, e.NAME""", nativeQuery = true)
    List<TimeRecord> getAttendanceDetails();

    @Query(value = """
            SELECT e.ID, e.NAME, COUNT(a.LATEARRIVAL) AS LATEARRIVALS,\s
            SUM(a.OVERTIMEHOURS) AS TOTALOVERTIMEHOURS,\s
            COUNT(CASE WHEN a.STATUS = 'ABSENT' THEN 1 ELSE NULL END) AS TOTALABSENTS,
            COUNT(CASE WHEN a.STATUS = 'PRESENT' THEN 1 ELSE NULL END) AS TOTALPRESENTS
            FROM Employee_G5_Jan16 e LEFT JOIN Attendance_G5_Jan16 a ON e.ID = a.EMPID\s
            WHERE e.ID = '202' AND
             a.ATTENDANCEDATE BETWEEN TO_DATE('2024-02-01', 'YYYY-MM-DD') AND\s
            TO_DATE('2024-03-01', 'YYYY-MM-DD') GROUP BY e.ID, e.NAME""", nativeQuery = true)
    TimeRecord getAttendanceDetailsById(Integer id);

}
