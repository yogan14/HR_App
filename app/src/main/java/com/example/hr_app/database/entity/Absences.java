package com.example.hr_app.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Absences
 * table of absences
 */
@Entity(tableName = "Absences",
        foreignKeys =
        @ForeignKey(
                entity = Collaborator.class,
                parentColumns = "email",
                childColumns = "email",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"email"}
                )}
)
public class Absences {

    @PrimaryKey(autoGenerate = true)
    private int idAbsence;

    private String startAbsence;

    private String endAbsence;

    private String reason;

    private String email;

    private boolean validate;

    public Absences(String startAbsence, String endAbsence, String reason, String email) {
        this.startAbsence = startAbsence;
        this.endAbsence = endAbsence;
        this.reason = reason;
        this.email = email;
        this.validate = false;
    }

    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    public String getStartAbsence() {
        return startAbsence;
    }

    public void setStartAbsence(String startAbsence) {
        this.startAbsence = startAbsence;
    }

    public String getEndAbsence() {
        return endAbsence;
    }

    public void setEndAbsence(String endAbsence) {
        this.endAbsence = endAbsence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Absences)) return false;
        Absences o = (Absences) obj;
        return o.getEmail() == this.getEmail();
    }

    @Override
    public String toString() {
        return startAbsence + " to " + endAbsence + " " + reason;
    }
}






























