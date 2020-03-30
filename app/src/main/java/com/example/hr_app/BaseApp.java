package com.example.hr_app;

import android.app.Application;

import com.example.hr_app.database.AppDatabase;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.database.repository.CollaboratorRepository;


/**
 * Android Application class. Used for accessing singletons and stock some variables usefull
 */
public class BaseApp extends Application {

    //stock of the variables mail(the user connected), mailCollaborator(for go to the details of a specific collaborator),
    //id(for go to a specific absence), idHR(for display the good menu if the user is HR or not)
    private String mailCollaborator;
    private String mail;
    private int id;
    private boolean isHR;

    //getters and setters
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

    public String getMailCollaborator() {
        return mailCollaborator;
    }

    public void setMailCollaborator(String variable) {
        this.mailCollaborator = variable;
    }

    public boolean getIsHR() {
        return isHR;
    }

    public void setHR(boolean HR) {
        isHR = HR;
    }

    /**
     * the methods for call the singletons.
     */
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