package com.example.hr_app.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "absences_table", foreignKeys = @ForeignKey(entity = Collaborator.class,
                                                                parentColumns = "idCollaborator",
                                                                childColumns = "IdCollaborator"))
public class Absences {

    @PrimaryKey(autoGenerate = true)
    private  int idAbsence;

    @ColumnInfo(name = "startAbsence")
    private Date startAbsence;

    @ColumnInfo(name = "endAbsence")
    private Date endAbsence;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "idCollaborator")
    private int idCollaborator;

    @ColumnInfo(name = "validate")
    private boolean validate;

    public Absences(Date startAbsence, Date endAbsence, String reason, int idCollaborator) {
        this.startAbsence = startAbsence;
        this.endAbsence = endAbsence;
        this.reason = reason;
        this.idCollaborator = idCollaborator;
        this.validate = false;
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

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
}
