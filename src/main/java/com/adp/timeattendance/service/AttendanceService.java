package com.adp.timeattendance.service;

import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendances(){
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByEmployeeId(Employee employeeId){
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    public Attendance createAttendance(Attendance attendance){
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Attendance attendance){
       return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Integer id){
        attendanceRepository.deleteById(id);
    }


}
