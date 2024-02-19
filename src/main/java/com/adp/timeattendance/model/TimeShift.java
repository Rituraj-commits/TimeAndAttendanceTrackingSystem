package com.adp.timeattendance.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Time;

@Entity
@Component
@Table(name = "TimeShift_G5_Jan16")
public class TimeShift {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column
    private Time shiftIn;

    @Column
    private Time shiftOut;

    public TimeShift() {
    }

    public TimeShift(Integer id, Time shiftIn, Time shiftOut) {
        this.id = id;
        this.shiftIn = shiftIn;
        this.shiftOut = shiftOut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getShiftIn() {
        return shiftIn;
    }

    public Time getShiftOut() {
        return shiftOut;
    }

    public void setShiftOut(Time shiftOut) {
        this.shiftOut = shiftOut;
    }

    public void setShiftIn(Time shiftIn) {
        this.shiftIn = shiftIn;
    }

    @Override
    public String toString() {
        return "TimeShift{" +
                "id=" + id +
                ", shiftIn=" + shiftIn +
                ", shiftOut=" + shiftOut +
                '}';
    }


}
