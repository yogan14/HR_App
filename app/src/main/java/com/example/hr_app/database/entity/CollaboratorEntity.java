package com.example.hr_app.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Collaborator
 * table of collaborators
 */

public class CollaboratorEntity implements Comparable {

    private String id;
    private String email;
    private String name;
    private String service;
    private String password;

    public CollaboratorEntity() {
    }

    public CollaboratorEntity(String name, String service, @NonNull String email, String password) {
        this.name = name;
        this.service = service;
        this.email = email;
        this.password = password;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Exclude
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
        if (!(obj instanceof CollaboratorEntity)) return false;
        CollaboratorEntity o = (CollaboratorEntity) obj;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("service", service);
        result.put("email", email);

        return result;
    }
}
