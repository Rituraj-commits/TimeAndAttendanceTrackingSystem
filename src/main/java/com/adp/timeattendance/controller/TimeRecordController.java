package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.service.TimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timerecord")
public class TimeRecordController {

    @Autowired
    TimeRecordService timeRecordService;

    @PostMapping
    public ResponseEntity<TimeRecord> addTimeRecord(@RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeRecordService.createTimeRecord(timeRecord));
    }


    @GetMapping
    public ResponseEntity<String> dummy() {
        return ResponseEntity.ok("API Calling");
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




}
