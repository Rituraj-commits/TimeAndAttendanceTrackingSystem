package com.adp.timeattendance.service;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.TimeRecordRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TimeRecordService {
    @Autowired
    private TimeRecordRepository timeRecordRepository;
    @Autowired // Set values for constructor
    private TimeShift timeShift;

    public List<TimeRecord> getTimeRecordByEmployeeId(Integer employeeId) {    // get employee attendance report by id
        return timeRecordRepository.findbyEmployeeId(employeeId);
    }

    public Integer calculateOvertimeHours(@NotNull TimeRecord timeRecord) {
        Timestamp t1 = timeShift.getShiftOut();
        Timestamp t2 = timeRecord.getClockTime();

        int comparisonResult = t1.compareTo(t2);

       
        boolean clock_out = timeRecord.getClockEvent().equals(ClockEvent.CLOCK_OUT);

        if (clock_out && (comparisonResult < 0)) {      // calculating overtime hours
            long milliseconds = t2.getTime() - t1.getTime();
            Integer hours = (int) milliseconds / (1000 * 60 * 60);

            timeRecord.setOvertimeHours(hours);

        }
        return timeRecord.getOvertimeHours();
    }

    public Boolean checkLateArrival(@NotNull TimeRecord timeRecord) {
        Timestamp t1 = timeShift.getShiftIn();
        Timestamp t2 = timeRecord.getClockTime();

        int comparisonResult = t1.compareTo(t2);

        boolean clock_in = timeRecord.getClockEvent().equals(ClockEvent.CLOCK_IN);


        return clock_in && (comparisonResult >= 0);
    }

    public TimeRecord createTimeRecord(TimeRecord timeRecord) {        // Calculate overtime hours and check Late Arrival
        Integer overtimehours = calculateOvertimeHours(timeRecord);
        Boolean isLate = checkLateArrival(timeRecord);

        timeRecord.setOvertimeHours(overtimehours);
        timeRecord.setLateArrival(isLate);

        if(timeRecord.getClockEvent() == null){
            timeRecord.setStatus(Status.ABSENT);
        }
        else timeRecord.setStatus(Status.PRESENT);

        return timeRecordRepository.save(timeRecord);


    }

    public List<TimeRecord> generateAttendanceReport(Integer employeeId){
        return timeRecordRepository.getAttendanceDetails(employeeId);
    }





}



