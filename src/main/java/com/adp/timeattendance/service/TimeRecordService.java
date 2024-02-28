package com.adp.timeattendance.service;

import com.adp.timeattendance.enums.ClockEvent;
import com.adp.timeattendance.enums.LateArrivalStatus;
import com.adp.timeattendance.enums.Status;
import com.adp.timeattendance.model.*;
import com.adp.timeattendance.repository.AttendanceRepository;
import com.adp.timeattendance.repository.TimeRecordRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

import java.util.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

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
		TimeRecord timeRecord = new TimeRecord(employee, clockTime, null, new java.sql.Date(attendanceDate.getTime()));   // date problem solved

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
		
		return attendanceRepository.save(attendance);
		

		
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

		Integer shift_hours = 9;
		Integer actual_hours = 0;

		Time t1 = timeShift.getShiftOut();
		Time t2 = timeShift.getShiftIn();
		Time t3 = timeRecord.getClockOut();
		Time t4 = timeRecord.getClockIn();

		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t3);
		System.out.println(t4);

		long shift_milliseconds;
		
		if(employee.getTimeShift().getId()==2){
			shift_milliseconds = (24 * 60 * 60 * 1000) - Math.abs(t1.getTime() - t2.getTime());
		}
		else {
			shift_milliseconds = Math.abs(t1.getTime() - t2.getTime());
		}
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

	public List<PayrollResponse> calculateAllPayroll(Integer id) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Double perDayPay = 2248.0;
		Double payPerHour = perDayPay/9.0;
		Double payroll = 0.0;
		List<PayrollResponse> payrollResponses = new ArrayList<>();

		MonthDate[] monthDates = objectMapper.readValue(new File("src/main/java/com/adp/timeattendance/months.json"),MonthDate[].class);
		for(MonthDate months:monthDates){
			Date startDate = null;
			try {
				System.out.println(months.getStartDate());
				startDate =  dateFormat.parse(months.getStartDate());
				System.out.println(startDate);


			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			Date endDate = null;
			try {
				System.out.println(months.getEndDate());
				endDate = dateFormat.parse(months.getEndDate());

			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

			AttendanceReport attendanceReport = timeRecordRepository.findAttendanceReportById(id,startDate,endDate);
			if(attendanceReport == null ) continue;
			else System.out.println("Not null");

			Integer overtimeHours = attendanceReport.getTotalOvertimeHours().intValue();
			Integer presentDays = attendanceReport.getTotalPresents().intValue();
			Double regularPay = presentDays.doubleValue()*perDayPay;
			regularPay = Math.round(regularPay* 10.0)/10.0;
			Double overtimePay = overtimeHours.doubleValue()*payPerHour;
			overtimePay = Math.round(overtimePay* 10.0)/10.0;

			System.out.println(attendanceReport.getTotalOvertimeHours().doubleValue());
			System.out.println(attendanceReport.getTotalPresents().doubleValue());
			payroll = attendanceReport.getTotalOvertimeHours().doubleValue()*payPerHour + attendanceReport.getTotalPresents().doubleValue()*perDayPay;
			payroll = Math.round(payroll* 10.0)/10.0;


			PayrollResponse payrollResponse = new PayrollResponse(payroll, overtimeHours, presentDays, regularPay, overtimePay,dateFormat.format(endDate));
			payrollResponses.add(payrollResponse);

		}
		return payrollResponses;
	}

	public PayrollResponse calculatePayroll(Integer id,String month,String year) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Double perDayPay = 2248.0;
		Double payPerHour = perDayPay/9.0;
		Double payroll = 0.0;
//		JSONObject jsonObject = new JSONObject(month);
//		String new_month = jsonObject.getString("month");
		
		String new_month = month;
		String new_year = year;
		MonthDate[] monthDates = objectMapper.readValue(new File("src/main/java/com/adp/timeattendance/months.json"),MonthDate[].class);
		for(MonthDate months:monthDates){
			System.out.println(months.getName());
			if(months.getName().equals(new_month) && months.getYear().equals(new_year)){
				Date startDate = null;
				try {
					System.out.println(months.getStartDate());
					startDate =  dateFormat.parse(months.getStartDate());
					System.out.println(startDate);


				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				Date endDate = null;
				try {
					System.out.println(months.getEndDate());
					endDate = dateFormat.parse(months.getEndDate());

				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				
//				DecimalFormat df = new DecimalFormat("#.##");
//				df.setMinimumFractionDigits(1);
//				df.setMaximumFractionDigits(2);

				AttendanceReport attendanceReport = timeRecordRepository.findAttendanceReportById(id,startDate,endDate);
				if(attendanceReport == null) return null;
				Integer overtimeHours = (Integer) attendanceReport.getTotalOvertimeHours().intValue();
				Integer presentDays = (Integer) attendanceReport.getTotalPresents().intValue();
				Double regularPay = presentDays.doubleValue()*perDayPay;
				regularPay = Math.round(regularPay* 10.0)/10.0;
				Double overtimePay = overtimeHours.doubleValue()*payPerHour;
				overtimePay = Math.round(overtimePay* 10.0)/10.0;
				
				System.out.println(attendanceReport.getTotalOvertimeHours().doubleValue());
				System.out.println(attendanceReport.getTotalPresents().doubleValue());
				payroll = attendanceReport.getTotalOvertimeHours().doubleValue()*payPerHour + attendanceReport.getTotalPresents().doubleValue()*perDayPay;
				payroll = Math.round(payroll* 10.0)/10.0;

				
				PayrollResponse payrollResponse = new PayrollResponse(payroll, overtimeHours, presentDays, regularPay, overtimePay,dateFormat.format(endDate));
				return payrollResponse;
			}
		}
		return null;

	}


}