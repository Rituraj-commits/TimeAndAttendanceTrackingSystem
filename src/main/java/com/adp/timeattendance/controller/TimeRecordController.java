package com.adp.timeattendance.controller;

import com.adp.timeattendance.model.AttendanceReport;
import com.adp.timeattendance.model.Employee;
import com.adp.timeattendance.model.PayrollResponse;
import com.adp.timeattendance.model.TimeRecord;
import com.adp.timeattendance.model.TimeRecordReq;
import com.adp.timeattendance.model.TimeRecordRequest;
import com.adp.timeattendance.repository.TimeRecordRepository;
import com.adp.timeattendance.service.EmployeeService;
import com.adp.timeattendance.service.TimeRecordService;

import ch.qos.logback.core.joran.conditional.IfAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/timerecord")
public class TimeRecordController {

	@Autowired
	TimeRecordService timeRecordService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	TimeRecordRepository timeRecordRepository;

//    @PostMapping
//    public ResponseEntity<TimeRecord> addTimeRecord(@RequestBody TimeRecordRequest timeRecordRequest) {
//
//        Employee employee = employeeService.read(timeRecordRequest.getEmployeeId());
//
//
//        return ResponseEntity.ok(timeRecordService.createTimeRecord(employee, timeRecordRequest.getClockIn(), timeRecordRequest.getClockOut(), timeRecordRequest.getAttendanceDate()));
//    }

//    @PutMapping
//    public ResponseEntity<TimeRecord> updateTimeRecord(@RequestBody TimeRecordRequest timeRecordRequest) {
//        Integer empId = timeRecordRequest.getEmployeeId();
//        Employee employee = employeeService.read(empId);
//        if (employee == null) return ResponseEntity.notFound().build();
//        Time clockInTime = timeRecordRequest.getClockIn();
//
//        List<TimeRecord> recordList = timeRecordService.getTimeRecordByEmployeeId(empId);
//
//        for (TimeRecord record : recordList) {
//            if (record.getEmployeeId().equals(employee) && record.getAttendanceDate().equals(timeRecordRequest.getAttendanceDate())) {    // If employee id is present in that particular date
//                return ResponseEntity.ok(timeRecordService.udpateTimeRecord(employee, record.getClockIn(), timeRecordRequest.getClockOut(), record.getAttendanceDate()));
//            }
//        }
//
//
//        return ResponseEntity.ok(timeRecordService.udpateTimeRecord(employee, timeRecordRequest.getClockIn(), timeRecordRequest.getClockOut(), timeRecordRequest.getAttendanceDate()));
//
//    }

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and #timeRecordReq.getEmployeeId() == authentication.principal.id")
	public ResponseEntity<TimeRecord> addTimeRecord(@RequestBody TimeRecordReq timeRecordReq) {

		Employee employee = employeeService.read(timeRecordReq.getEmployeeId());
		Date attendanceDate = timeRecordReq.getAttendanceDate();

		TimeRecord timeRecord = timeRecordService.getTimeRecordByEmpIdAndDate(timeRecordReq.getEmployeeId(),
				attendanceDate);

		if (timeRecord != null) {

			if (timeRecord.getClockOut() == null) {

				timeRecord.setClockOut(timeRecordReq.getClockTime());
			} else {
				System.out.println("Setting ClockOut to null.");
				timeRecord.setClockOut(null);

			}
					
			timeRecordRepository.save(timeRecord);
			timeRecordService.updateAttendance(timeRecord);
			return ResponseEntity.ok(timeRecord);

		} else {
			return ResponseEntity.ok(timeRecordService.createTimeRecord(employee, timeRecordReq.getClockTime(),
					timeRecordReq.getAttendanceDate()));
		}

	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<TimeRecord>> generateRecords() {
		List<TimeRecord> recordList = timeRecordService.getAllTimeRecords();
		return ResponseEntity.ok(recordList);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<TimeRecord>> generateRecordById(@PathVariable Integer id) {
		List<TimeRecord> recordList = timeRecordService.getTimeRecordByEmployeeId(id);
		return ResponseEntity.ok(recordList);
	}

	@GetMapping("/payroll/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and (hasAuthority('ADMIN') or authentication.principal.id == #id)")
	public ResponseEntity<List<PayrollResponse>> generateAllRecordById(@PathVariable Integer id) throws IOException {
		List<PayrollResponse> payrollResponses = timeRecordService.calculateAllPayroll(id);
		return ResponseEntity.ok(payrollResponses);
	}

	@GetMapping("/payroll/{id}/{month}/{year}")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and (hasAuthority('ADMIN') or authentication.principal.id == #id)")
	public ResponseEntity<PayrollResponse> generatePayroll(@PathVariable("id") Integer id, @PathVariable("month") String month, @PathVariable("year") String year) throws IOException {
		
		PayrollResponse payrollResponse = timeRecordService.calculatePayroll(id,month,year);
		if (payrollResponse!=null) return ResponseEntity.ok(payrollResponse);
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/report")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<AttendanceReport>> generateReport(@RequestParam("from") String fromDate,
			@RequestParam("to") String toDate) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date utilFromDate = sdf.parse(fromDate);
		java.util.Date utilToDate = sdf.parse(toDate);

		java.sql.Date sqlFromDate = new java.sql.Date(utilFromDate.getTime());
		java.sql.Date sqlToDate = new java.sql.Date(utilToDate.getTime());

		List<AttendanceReport> result = timeRecordService.generateAttendanceReport(sqlFromDate, sqlToDate);
		if (result != null)
			return ResponseEntity.ok(result);
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/report/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AttendanceReport> generateReportById(@PathVariable("id") Integer id,
			@RequestParam("from") String fromDate, @RequestParam("to") String toDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date utilFromDate = sdf.parse(fromDate);
		java.util.Date utilToDate = sdf.parse(toDate);

		java.sql.Date sqlFromDate = new java.sql.Date(utilFromDate.getTime());
		java.sql.Date sqlToDate = new java.sql.Date(utilToDate.getTime());

		AttendanceReport result = timeRecordService.generateAttendanceReportById(id, sqlFromDate, sqlToDate);
		if (result != null)
			return ResponseEntity.ok(result);
		return ResponseEntity.notFound().build();
	}


}
