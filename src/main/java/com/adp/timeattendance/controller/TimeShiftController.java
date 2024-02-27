package com.adp.timeattendance.controller;


import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.TimeShiftRepository;
import com.adp.timeattendance.service.TimeShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/timeshift")
public class TimeShiftController {

    @Autowired
    TimeShiftService timeShiftService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<TimeShift> retrieveShift(@PathVariable Integer id){
        TimeShift newTimeShift = timeShiftService.getShift(id);
        if(newTimeShift!=null) return  ResponseEntity.ok(newTimeShift);
        return ResponseEntity.notFound().build();
    }


}
