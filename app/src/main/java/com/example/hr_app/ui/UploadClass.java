package com.example.hr_app.ui;

import com.google.firebase.database.Exclude;

public class UploadClass {
    private String name;
    private String url;
    private String key;


    public UploadClass() {
        //empty constructor needed
    }

    public UploadClass(String name, String url) {
        if (name.trim().equals("")) {
            name = "No name";
        }
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
