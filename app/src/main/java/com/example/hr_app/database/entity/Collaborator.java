package com.example.hr_app.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * Collaborator
 * table of collaborators
 */
@Entity(tableName = "collaborator", primaryKeys = {"email"})
public class Collaborator implements Comparable {

    @NonNull
    private String email;

    private String name;

    private String service;

    @NonNull
    private String password;

    @Ignore
    public Collaborator(){
    }

    public Collaborator(String name, String service, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.service = service;
        this.email = email;
        this.password = password;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Collaborator)) return false;
        Collaborator o = (Collaborator) obj;
        return o.getEmail() == this.getEmail();
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
