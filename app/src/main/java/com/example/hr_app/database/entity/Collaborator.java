package com.example.hr_app.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "collaborator")
public class Collaborator implements Comparable {

    @PrimaryKey(autoGenerate = true)
    private int idCollaborator;

    private String name;

    private String service;

    private String email;

    private String password;

    private boolean isHR;

    public Collaborator(String name, String service, String email, String password) {
        this.name = name;
        this.service = service;
        this.email = email;
        this.password = password;

        if(service.equals("HR")){
            this.isHR = true;
        } else {
            this.isHR = false;
        }

    }

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHR() {
        return isHR;
    }

    public void setHR(boolean HR) {
        isHR = HR;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Collaborator)) return false;
        Collaborator o = (Collaborator) obj;
        return o.getIdCollaborator() == this.getIdCollaborator();
    }

    @Override
    public String toString() {
        return name + " " + service;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

}
