package com.adp.timeattendance.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Table(name="TimeShift_G5_Jan16")
public class TimeShift {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Timestamp shiftIn;

    @Column
    private Timestamp shiftOut;

    public TimeShift() {
    }

    public TimeShift(Integer id, Timestamp shiftIn, Timestamp shiftOut) {
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

    public Timestamp getShiftIn() {
        return shiftIn;
    }

    public Timestamp getShiftOut() {
        return shiftOut;
    }

    public void setShiftOut(Timestamp shiftOut) {
        this.shiftOut = shiftOut;
    }

    public void setShiftIn(Timestamp shiftIn) {
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
