package com.adp.timeattendance.service;

import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.TimeShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class TimeShiftService {

        @Autowired
        private TimeShiftRepository timeShiftRepository;

        public TimeShift getShift(Integer shiftId)
        {

                return timeShiftRepository.findById(shiftId).orElse(null);

        }



}
