package com.adp.timeattendance.service;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.Attendance;
import com.adp.timeattendance.model.AttendanceReport;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeShift;
import com.adp.timeattendance.repository.AttendanceRepository;
import com.adp.timeattendance.repository.TimeRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class TimeRecordService {
	@Autowired
	private TimeRecordRepository timeRecordRepository;

	// check
	@Autowired // Set values for constructor
	private TimeShift timeShift;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private AttendanceService attendanceService;

	public List<TimeRecord> getTimeRecordByEmployeeId(Integer employeeId) { // get employee attendance report by id

		Employee employee = employeeService.read(employeeId);
		if (employee != null) {
			return timeRecordRepository.findByEmployeeId(employee);
		}

		return null;
	}

//    public TimeRecord createTimeRecord(Employee employee,Time clockIn,Time clockOut,Date attendanceDate) {
//
//        TimeRecord timeRecord = new TimeRecord(employee,clockIn,clockOut,attendanceDate);
//        Attendance attendance = new Attendance();
//        attendance.setOvertimeHours(calculateOvertimeHours(timeRecord));
//        attendance.setLateArrival((checkLateArrival(timeRecord)) ? LateArrivalStatus.LATE : LateArrivalStatus.NOT_LATE);
//        attendance.setStatus((checkStatus(timeRecord)) ? Status.PRESENT : Status.ABSENT);
//        attendance.setEmployeeId(timeRecord.getEmployeeId());
//        attendance.setAttendanceDate(timeRecord.getAttendanceDate());
//        attendanceRepository.save(attendance);
//        return timeRecordRepository.save(timeRecord);
//    }

//	public TimeRecord udpateTimeRecord(Employee employee, Time clockIn, Time clockOut, Date attendanceDate) {
//
//		List<TimeRecord> recordList = getTimeRecordByEmployeeId(employee.getId());
//		Attendance attendance = new Attendance();
//		for (TimeRecord record : recordList) {
//			if (record.getEmployeeId().equals(employee) && record.getAttendanceDate().equals(attendanceDate)) { // If
//																												// employee
//																												// id is
//																												// present
//																												// in
//																												// that
//																												// particular
//																												// date
//
//				Integer overtimeHours = calculateOvertimeHours(record);
//				attendance = attendanceService.getAttendanceByIdAndDate(record.getEmployeeId().getId(),
//						record.getAttendanceDate()); // fixed here
//				attendance.setOvertimeHours(overtimeHours);
//				record.setClockOut(clockOut);
//				attendanceRepository.save(attendance);
//				return timeRecordRepository.save(record);
//
//			}
//		}
//		return null;
//
//	}

	public TimeRecord getTimeRecordByEmpIdAndDate(Integer employeeId, Date attendanceDate) {
		Employee employee = employeeService.read(employeeId);
		List<TimeRecord> recordsList = timeRecordRepository.findByEmployeeId(employee);

		if (recordsList != null) {
			for (TimeRecord record : recordsList) {
				if (record.getAttendanceDate().equals(attendanceDate)) {
					return record;
				}
			}
		}

		return null;

	}

	public TimeRecord createTimeRecord(Employee employee, Time clockTime, Date attendanceDate) {
		TimeRecord timeRecord = new TimeRecord(employee, clockTime, null, attendanceDate);

		Attendance attendance = new Attendance();

		attendance.setOvertimeHours(calculateOvertimeHours(timeRecord));
		attendance.setLateArrival((checkLateArrival(timeRecord)) ? LateArrivalStatus.LATE : LateArrivalStatus.NOT_LATE);
		attendance.setStatus((checkStatus(timeRecord)) ? Status.PRESENT : Status.ABSENT);
		attendance.setEmployeeId(timeRecord.getEmployeeId());
		attendance.setAttendanceDate(timeRecord.getAttendanceDate());

		attendanceRepository.save(attendance);
		return timeRecordRepository.save(timeRecord);

	}

	public Attendance updateAttendance(TimeRecord timeRecord) {
		
		Date attendanceDate = timeRecord.getAttendanceDate();
		Employee employee = timeRecord.getEmployeeId();
	    Integer employeeId = employee.getId();
		Attendance attendance = new Attendance();

		attendance = attendanceService.getAttendanceByIdAndDate(employeeId, attendanceDate);

		if (attendance != null) {
			Integer overtimeHours = calculateOvertimeHours(timeRecord);
			attendance.setOvertimeHours(overtimeHours);
		}
		
		attendanceRepository.save(attendance);
		
		return attendance;
		
		
	}

	public List<TimeRecord> getAllTimeRecords() {
		return timeRecordRepository.findAll();
	}

	public List<AttendanceReport> generateAttendanceReport(Date fromDate, Date toDate) {
		return timeRecordRepository.findAttendanceReport(fromDate, toDate);
	}

	public AttendanceReport generateAttendanceReportById(Integer id, Date fromDate, Date toDate) {
		return timeRecordRepository.findAttendanceReportById(id, fromDate, toDate);
	}

	public List<TimeRecord> deleteTimeRecordById(Integer id) {
		Employee employee = employeeService.read(id);
		List<TimeRecord> temp = timeRecordRepository.findByEmployeeId(employee);
		if (temp != null) {
			timeRecordRepository.deleteAllInBatch(temp);
		}
		return temp;
	}

	public Integer calculateOvertimeHours(TimeRecord timeRecord) {

		if (timeRecord.getClockIn() == null || timeRecord.getClockOut() == null)
			return 0;

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

		long shift_milliseconds = (24 * 60 * 60 * 1000) - Math.abs(t1.getTime() - t2.getTime());
		shift_hours = (int) shift_milliseconds / (1000 * 60 * 60);

		System.out.println(shift_hours);

		long actual_milliseconds;
		if (t3.after(t4)) {
			actual_milliseconds = Math.abs(t3.getTime() - t4.getTime());
		} else {
			actual_milliseconds = (24 * 60 * 60 * 1000) - Math.abs(t3.getTime() - t4.getTime());
		}
		actual_hours = (int) actual_milliseconds / (1000 * 60 * 60);
		System.out.println(actual_hours);

		if (actual_hours > shift_hours)
			return actual_hours - shift_hours;
		else
			return 0;
	}

	public Boolean checkStatus(TimeRecord timeRecord) {
		return timeRecord.getClockIn() != null;
	}

	public Boolean checkLateArrival(TimeRecord timeRecord) {

		if (timeRecord.getClockIn() == null)
			return false;

		Employee employee = timeRecord.getEmployeeId();

		TimeShift timeShift = employee.getTimeShift();

		Time t1 = timeShift.getShiftIn();

		Time t2 = timeRecord.getClockIn();

		return t1.compareTo(t2) < 0;

	}

//    public TimeRecord createTimeRecordByForm(Integer employeeId, Time clockIn, Time clockOut, Date attendanceDate)
//    {
//        Employee employee = employeeService.read(employeeId);
//
//
//    }
//}
}