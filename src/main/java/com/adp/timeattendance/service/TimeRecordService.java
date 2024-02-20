package com.adp.timeattendance.service;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.AttendanceRepository;
import com.adp.timeattendance.repository.TimeRecordRepository;


import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class TimeRecordService {
    @Autowired
    private TimeRecordRepository timeRecordRepository;

    //check
    @Autowired // Set values for constructor
    private TimeShift timeShift;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<TimeRecord> getTimeRecordByEmployeeId(Integer employeeId) { // get employee attendance report by id

        Employee employee = employeeService.read(employeeId);
        if (employee != null) {
            return timeRecordRepository.findByEmployeeId(employee);
        }

        return null;
    }


    public TimeRecord createTimeRecord(TimeRecord timeRecord) {
		Attendance attendance = new Attendance();
		attendance.setOvertimeHours(calculateOvertimeHours(timeRecord));
		attendance.setLateArrival((checkLateArrival(timeRecord))?LateArrivalStatus.LATE:LateArrivalStatus.NOT_LATE);
		attendance.setStatus((checkStatus(timeRecord))?Status.PRESENT:Status.ABSENT);
		attendance.setEmployeeId(timeRecord.getEmployeeId());
        attendance.setAttendanceDate(timeRecord.getAttendanceDate());
        attendanceRepository.save(attendance);
        return timeRecordRepository.save(timeRecord);
    }

    public List<TimeRecord> generateAttendanceReport() {
        return timeRecordRepository.getAttendanceDetails();
    }

    public List<TimeRecord> getAllTimeRecords()  { return timeRecordRepository.findAll(); }

    public TimeRecord generateAttendanceReportById(Integer id) {
        return timeRecordRepository.getAttendanceDetailsById(id);
    }

    public List<TimeRecord> deleteTimeRecordById(Integer id){
        Employee employee = employeeService.read(id);
        List<TimeRecord> temp = timeRecordRepository.findByEmployeeId(employee);
        if(temp!=null)
        {
            timeRecordRepository.deleteAllInBatch(temp);
        }
        return temp;
    }




    public Integer calculateOvertimeHours(TimeRecord timeRecord) {

        Employee employee = timeRecord.getEmployeeId();

        TimeShift timeShift = employee.getTimeShift();

        Integer hours = 0;

        Time t1 = timeShift.getShiftOut();
        Time t2 = timeRecord.getClockOut();

        int comparisonResult = t1.compareTo(t2);

        if (comparisonResult < 0) { // calculating overtime hours
            long milliseconds = t2.getTime() - t1.getTime();
            hours = (int) milliseconds / (1000 * 60 * 60);

        }
        return hours;  //set overtime hours in attendance
    }

    public Boolean checkStatus(TimeRecord timeRecord) {
        return timeRecord.getClockIn() != null;
    }

    public Boolean checkLateArrival(TimeRecord timeRecord) {

        Employee employee = timeRecord.getEmployeeId();

        TimeShift timeShift = employee.getTimeShift();

        Time t1 = timeShift.getShiftIn();

        Time t2 = timeRecord.getClockIn();

        int comparisonResult = t1.compareTo(t2);

        if (comparisonResult >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
