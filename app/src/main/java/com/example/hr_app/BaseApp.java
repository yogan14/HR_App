package com.example.hr_app;

import android.app.Application;

import com.example.hr_app.database.AppDatabase;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.database.repository.CollaboratorRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    private String mail;
    private int id;

    public String getTheMail() {
        return mail;
    }

    public void setTheMail(String variable) {
        this.mail = variable;
    }

    public int getTheID(){
        return id;
    }

    public void setTheID(int id){
        this.id = id;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public AbsencesRepository getAbsenceRepository() {
        return AbsencesRepository.getInstance();
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return CollaboratorRepository.getInstance();
    }
}