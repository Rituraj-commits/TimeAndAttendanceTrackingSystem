package com.adp.timeattendance.controller;


import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<Attendance>> retrieveAllAttendance(){
        List<Attendance> attendances = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Attendance>> retrieveAttendanceById(@PathVariable Integer id){
        List<Attendance> attendance = attendanceService.getAttendanceById(id);
        if(attendance!=null) return ResponseEntity.ok(attendance);
        return ResponseEntity.notFound().build();

    }


}
