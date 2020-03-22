package com.example.hr_app.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Absences",
        foreignKeys =
        @ForeignKey(
                entity = Collaborator.class,
                parentColumns = "idCollaborator",
                childColumns = "idCollaborator",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"idCollaborator"}
                )}
)
public class Absences {

    @PrimaryKey(autoGenerate = true)
    private int idAbsence;

    private String startAbsence;

    private String endAbsence;

    private String reason;

    private int idCollaborator;

    private boolean validate;

    public Absences(String startAbsence, String endAbsence, String reason, int idCollaborator) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Absences)) return false;
        Absences o = (Absences) obj;
        return o.getIdCollaborator() == this.getIdCollaborator();
    }

    @Override
    public String toString() {
        return startAbsence + " to " + endAbsence + " " + reason;
    }
}
