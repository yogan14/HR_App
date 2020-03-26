package com.example.hr_app;

import android.app.Application;

import com.example.hr_app.database.AppDatabase;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.database.repository.CollaboratorRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    private String someVariable;

    public String getTheMail() {
        return someVariable;
    }

    private int test;

    public int getTheAbsenceID(){
        return test;
    }

    public void setTheAbsenceID(int id){
        this.test = id;
    }

    public void setTheMail(String variable) {
        this.someVariable = variable;
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