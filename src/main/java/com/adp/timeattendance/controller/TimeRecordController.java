package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.service.TimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/timerecord")
public class TimeRecordController {

    @Autowired
    TimeRecordService timeRecordService;

    @PostMapping
    public ResponseEntity<TimeRecord> addTimeRecord(@RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeRecordService.createTimeRecord(timeRecord));
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
    public ResponseEntity<List<TimeRecord>> generateReport() {
        return ResponseEntity.ok(timeRecordService.generateAttendanceReport());
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<TimeRecord> generateReportById(@PathVariable Integer id){
        return ResponseEntity.ok(timeRecordService.generateAttendanceReportById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<TimeRecord>> removeTimeRecord(@PathVariable Integer id){
        List<TimeRecord> deletedRecords = timeRecordService.deleteTimeRecordById(id);
        if(deletedRecords!=null) return  ResponseEntity.ok(deletedRecords);
        return ResponseEntity.notFound().build();
    }





}
