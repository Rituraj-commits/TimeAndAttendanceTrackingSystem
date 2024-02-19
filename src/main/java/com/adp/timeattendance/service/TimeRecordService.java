package com.adp.timeattendance.service;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.TimeRecordRepository;

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
	
	public List<TimeRecord> getTimeRecordByEmployeeId(Integer employeeId) { // get employee attendance report by id
		
		Employee employee = employeeService.read(employeeId);
		if(employee != null) {
			return timeRecordRepository.findByEmployeeId(employee);
		}
		
		return null;
	}



	public Integer calculateOvertimeHours(TimeRecord timeRecord) {

		Time t1 = timeShift.getShiftOut();
		Time t2 = timeRecord.getClockTime();

		int comparisonResult = t1.compareTo(t2);

		boolean clock_out = timeRecord.getClockEvent().equals(ClockEvent.CLOCK_OUT);

		if (clock_out && (comparisonResult < 0)) { // calculating overtime hours
			long milliseconds = t2.getTime() - t1.getTime();
			Integer hours = (int) milliseconds / (1000 * 60 * 60);

			timeRecord.setOvertimeHours(hours);

		}
		return timeRecord.getOvertimeHours();
	}

	public Boolean checkLateArrival(TimeRecord timeRecord) {
		Time t1 = timeShift.getShiftIn();
		Time t2 = timeRecord.getClockTime();

		int comparisonResult = t1.compareTo(t2);

		boolean clock_in = timeRecord.getClockEvent().equals(ClockEvent.CLOCK_IN);

		if (clock_in && (comparisonResult >= 0)) {
			return true;
		} else {
			return false;
		}
	}

	public TimeRecord createTimeRecord(TimeRecord timeRecord) { // Calculate overtime hours and check Late Arrival
		Integer overtimehours = calculateOvertimeHours(timeRecord);
		Boolean isLate = checkLateArrival(timeRecord);

		timeRecord.setOvertimeHours(overtimehours);

		if (isLate)
			timeRecord.setLateArrival(LateArrivalStatus.LATE);
		else
			timeRecord.setLateArrival(LateArrivalStatus.NOT_LATE);

		if (timeRecord.getClockEvent() == null) {
			timeRecord.setStatus(Status.ABSENT);
		} else {
			timeRecord.setStatus(Status.PRESENT);
		}

		return timeRecordRepository.save(timeRecord);

	}

	public List<TimeRecord> generateAttendanceReport() {
		return timeRecordRepository.getAttendanceDetails();
	}

	public TimeRecord generateAttendanceReportById(Integer id){
		return timeRecordRepository.getAttendanceDetailsById(id);
	}

}
