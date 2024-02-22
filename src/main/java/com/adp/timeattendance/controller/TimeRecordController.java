package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.AttendanceReport;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeRecordRequest;
import com.adp.timeattendance.service.EmployeeService;
import com.adp.timeattendance.service.TimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/timerecord")
public class TimeRecordController {

    @Autowired
    TimeRecordService timeRecordService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<TimeRecord> addTimeRecord(@RequestBody TimeRecordRequest timeRecordRequest) {

        Employee employee = employeeService.read(timeRecordRequest.getEmployeeId());


        return ResponseEntity.ok(timeRecordService.createTimeRecord(employee, timeRecordRequest.getClockIn(), timeRecordRequest.getClockOut(), timeRecordRequest.getAttendanceDate()));
    }

    @PutMapping
    public ResponseEntity<TimeRecord> updateTimeRecord(@RequestBody TimeRecordRequest timeRecordRequest) {
        Integer empId = timeRecordRequest.getEmployeeId();
        Employee employee = employeeService.read(empId);
        if (employee == null) return ResponseEntity.notFound().build();
        Time clockInTime = timeRecordRequest.getClockIn();

        List<TimeRecord> recordList = timeRecordService.getTimeRecordByEmployeeId(empId);

        for (TimeRecord record : recordList) {
            if (record.getEmployeeId().equals(employee) && record.getAttendanceDate().equals(timeRecordRequest.getAttendanceDate())) {    // If employee id is present in that particular date
                return ResponseEntity.ok(timeRecordService.udpateTimeRecord(employee, record.getClockIn(), timeRecordRequest.getClockOut(), record.getAttendanceDate()));
            }
        }


        return ResponseEntity.ok(timeRecordService.udpateTimeRecord(employee, timeRecordRequest.getClockIn(), timeRecordRequest.getClockOut(), timeRecordRequest.getAttendanceDate()));

    }


    @GetMapping
    public ResponseEntity<List<TimeRecord>> generateRecords() {
        List<TimeRecord> recordList = timeRecordService.getAllTimeRecords();
        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TimeRecord>> generateRecordById(@PathVariable Integer id) {
        List<TimeRecord> recordList = timeRecordService.getTimeRecordByEmployeeId(id);
        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/report")
    public ResponseEntity<List<AttendanceReport>> generateReport(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                                 @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        List<AttendanceReport> result = timeRecordService.generateAttendanceReport(fromDate, toDate);
        if (result != null) return ResponseEntity.ok(result);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<AttendanceReport> generateReportById(@PathVariable("id") Integer id,
                                                               @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                               @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        AttendanceReport result = timeRecordService.generateAttendanceReportById(id, fromDate, toDate);
        if (result != null) return ResponseEntity.ok(result);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<TimeRecord>> removeTimeRecord(@PathVariable Integer id) {
        List<TimeRecord> deletedRecords = timeRecordService.deleteTimeRecordById(id);
        if (deletedRecords != null) return ResponseEntity.ok(deletedRecords);
        return ResponseEntity.notFound().build();
    }


}
