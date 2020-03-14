package com.example.hr_app.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "absences_table")
public class Absences {

    @PrimaryKey(autoGenerate = true)
    private  int idAbsence;

    private Date startAbsence;

    private Date endAbsence;

    private String reason;

    public Absences(Date startAbsence, Date endAbsence, String reason) {
        this.startAbsence = startAbsence;
        this.endAbsence = endAbsence;
        this.reason = reason;
    }

    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Date getStartAbsence() {
        return startAbsence;
    }

    public void setStartAbsence(Date startAbsence) {
        this.startAbsence = startAbsence;
    }

    public Date getEndAbsence() {
        return endAbsence;
    }

    public void setEndAbsence(Date endAbsence) {
        this.endAbsence = endAbsence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
