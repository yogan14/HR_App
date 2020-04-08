package com.example.hr_app.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Absences
 * table of absences
 */

public class AbsencesEntity {

    private String idAbsence;
    private String startAbsence;
    private String endAbsence;
    private String reason;
    private String email;

    public AbsencesEntity() {

    }

    @Exclude
    public String getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(String idAbsence) {
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

    @Exclude
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof AbsencesEntity)) return false;
        AbsencesEntity o = (AbsencesEntity) obj;
        return o.getEmail() == this.getEmail();
    }

    @Override
    public String toString() {
        return startAbsence + " to " + endAbsence + " " + reason;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("startAbsence", startAbsence);
        result.put("endAbsence", endAbsence);
        result.put("reason", reason);

        return result;
    }
}
