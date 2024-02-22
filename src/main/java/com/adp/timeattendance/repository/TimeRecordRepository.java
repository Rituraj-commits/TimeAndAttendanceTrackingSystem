package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.AttendanceReport;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {
    List<TimeRecord> findByEmployeeId(Employee employeeId);

    @Query(value = """
            SELECT e.ID, e.NAME, 
            COUNT(case WHEN a.LATEARRIVAL = 'LATE' THEN 1 ELSE NULL END) AS LATEARRIVALS,
            SUM(a.OVERTIMEHOURS) AS TOTALOVERTIMEHOURS,
            COUNT(CASE WHEN a.STATUS = 'ABSENT' THEN 1 ELSE NULL END) AS TOTALABSENTS,
            COUNT(CASE WHEN a.STATUS = 'PRESENT' THEN 1 ELSE NULL END) AS TOTALPRESENTS
            FROM Employee_G5_Jan16 e LEFT JOIN Attendance_G5_Jan16 a ON e.ID = a.EMPID
            WHERE a.ATTENDANCEDATE BETWEEN :fromDate AND
            :toDate GROUP BY e.ID, e.NAME""", nativeQuery = true)
    List<Object[]> getAttendanceDetails(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    default List<AttendanceReport> findAttendanceReport(Date fromDate, Date toDate) {
        List<Object[]> data = getAttendanceDetails(fromDate, toDate);
        
        if(data.size() == 0)
        {
        	return null;
        }
        
        List<AttendanceReport> reports = new ArrayList<>();
        for (Object[] row : data) {
            AttendanceReport report = new AttendanceReport(
                    (Integer) row[0],
                    (String) row[1],
                    (BigDecimal) row[2],
                    (BigDecimal) row[3],
                    (BigDecimal) row[4],
                    (BigDecimal) row[5]
            );
            reports.add(report);
        }
        return reports;
    }


//    @Query(value = """
//            SELECT e.ID, e.NAME, 
//            COUNT(case WHEN a.LATEARRIVAL = 'LATE' THEN 1 ELSE NULL END) AS LATEARRIVALS,
//            SUM(a.OVERTIMEHOURS) AS TOTALOVERTIMEHOURS,
//            COUNT(CASE WHEN a.STATUS = 'ABSENT' THEN 1 ELSE NULL END) AS TOTALABSENTS,
//            COUNT(CASE WHEN a.STATUS = 'PRESENT' THEN 1 ELSE NULL END) AS TOTALPRESENTS
//            FROM Employee_G5_Jan16 e LEFT JOIN Attendance_G5_Jan16 a ON e.ID = a.EMPID
//            WHERE e.ID = :id AND
//             a.ATTENDANCEDATE BETWEEN TO_DATE('2024-02-01', 'YYYY-MM-DD') AND
//            TO_DATE('2024-03-01', 'YYYY-MM-DD') GROUP BY e.ID, e.NAME""", nativeQuery = true)
    
    @Query(value = """
            SELECT e.ID, e.NAME, 
            COUNT(case WHEN a.LATEARRIVAL = 'LATE' THEN 1 ELSE NULL END) AS LATEARRIVALS,
            SUM(a.OVERTIMEHOURS) AS TOTALOVERTIMEHOURS,
            COUNT(CASE WHEN a.STATUS = 'ABSENT' THEN 1 ELSE NULL END) AS TOTALABSENTS,
            COUNT(CASE WHEN a.STATUS = 'PRESENT' THEN 1 ELSE NULL END) AS TOTALPRESENTS
            FROM Employee_G5_Jan16 e LEFT JOIN Attendance_G5_Jan16 a ON e.ID = a.EMPID
            WHERE e.ID = :id AND
             a.ATTENDANCEDATE BETWEEN :fromDate AND
            :toDate GROUP BY e.ID, e.NAME""", nativeQuery = true)
    List<Object[]> getAttendanceDetailsById(@Param("id") Integer id, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate );
    
    default AttendanceReport findAttendanceReportById(Integer id, Date fromDate, Date toDate) {
        List<Object[]> data = getAttendanceDetailsById(id, fromDate, toDate);
        
        if(data.size() == 0)
        {
        	return null;
        }
        
        Object[] row = data.get(0);
        
        AttendanceReport report = new AttendanceReport(
                (Integer) row[0],
                (String) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3],
                (BigDecimal) row[4],
                (BigDecimal) row[5]
        );
        return report;
    }

}
