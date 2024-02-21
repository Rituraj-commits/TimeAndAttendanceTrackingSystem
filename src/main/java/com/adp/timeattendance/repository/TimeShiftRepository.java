package com.adp.timeattendance.repository;

import com.adp.timeattendance.model.TimeShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeShiftRepository extends JpaRepository<TimeShift, Integer> {
}
