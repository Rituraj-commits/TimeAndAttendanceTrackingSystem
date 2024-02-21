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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

        Integer shift_hours = 0;
        Integer actual_hours = 0;

        Time t1 = timeShift.getShiftOut();
        Time t2 = timeShift.getShiftIn();
        Time t3 = timeRecord.getClockOut();
        Time t4 = timeRecord.getClockIn();

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t4);


        long shift_milliseconds = (24*60*60*1000) - Math.abs(t1.getTime() - t2.getTime());
        shift_hours = (int) shift_milliseconds / (1000 * 60 * 60);

        System.out.println(shift_hours);

        long actual_milliseconds;
        if(t3.after(t4)){
            actual_milliseconds = Math.abs(t3.getTime()- t4.getTime());
        }
        else {
            actual_milliseconds = (24*60*60*1000) - Math.abs(t3.getTime()-t4.getTime());
        }
        actual_hours = (int) actual_milliseconds / (1000 * 60 * 60);
        System.out.println(actual_hours);

       if(actual_hours>shift_hours) return actual_hours-shift_hours;
       else return 0;
    }

    public Boolean checkStatus(TimeRecord timeRecord) {
        return timeRecord.getClockIn() != null;
    }

    public Boolean checkLateArrival(TimeRecord timeRecord) {

        Employee employee = timeRecord.getEmployeeId();

        TimeShift timeShift = employee.getTimeShift();

        Time t1 = timeShift.getShiftIn();

        Time t2 = timeRecord.getClockIn();

        return t1.compareTo(t2)<0;


    }
}
