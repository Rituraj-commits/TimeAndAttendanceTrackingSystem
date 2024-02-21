package com.adp.timeattendance.controller;


import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.TimeShiftRepository;
import com.adp.timeattendance.service.TimeShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/timeshift")
public class TimeShiftController {

    @Autowired
    TimeShiftService timeShiftService;

    @GetMapping("/{id}")
    public ResponseEntity<TimeShift> retrieveShift(@PathVariable Integer id){
        return ResponseEntity.ok(timeShiftService.getShift(id));
    }


}
