package com.example.hr_app.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "collaboratorTable")
public class Collaborator {

    @PrimaryKey
    private int idCollaborator;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "service")
    private String service;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "isHR")
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
}
