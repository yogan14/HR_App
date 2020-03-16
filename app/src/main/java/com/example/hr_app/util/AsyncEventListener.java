package com.example.hr_app.util;

public interface AsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
