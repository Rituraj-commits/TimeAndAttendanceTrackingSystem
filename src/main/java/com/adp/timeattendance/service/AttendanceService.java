package com.adp.timeattendance.service;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.AttendanceRepository;

public class AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	public Attendance createAttendance(Attendance attendance)
	{
		return attendanceRepository.save(attendance);
	}
	
	public List<Attendance> getAttendanceByEmployeeId(Integer employeeId)
	{
		Employee employee = employeeService.read(employeeId);
		if(employee != null) {
			return attendanceRepository.findByEmployeeId(employee);
		}
		
		return null;
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
	
	public Boolean checkLateArrival(TimeRecord timeRecord) {
		
		Employee employee = timeRecord.getEmployeeId();
		
		TimeShift timeShift = employee.getTimeShift();
		
		Time t1 = timeShift.getShiftIn();

		Time t2 = timeRecord.getClockIn();

		int comparisonResult = t1.compareTo(t2);

		if (comparisonResult >= 0){
			return true;
		} else {
			return false;
		}
	}
	
	

}
