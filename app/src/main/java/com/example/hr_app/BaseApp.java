package com.example.hr_app;

import android.app.Application;

import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.database.repository.CollaboratorRepository;


/**
 * Android Application class. Used for accessing singletons and stock some variables usefull
 */
public class BaseApp extends Application {

    //stock of the variables mail(the user connected), mailCollaborator(for go to the details of a specific collaborator),
    //id(for go to a specific absence), idHR(for display the good menu if the user is HR or not)
    private String idCollaborator;
    private String mail;
    private String idAbsence;
    private boolean isHR;

    //getters and setters
    public String getTheMail() {
        return mail;
    }

    public void setTheMail(String variable) {
        this.mail = variable;
    }

    public String getIDAbsence(){
        return idAbsence;
    }

    public void setIDAbsence(String id){
        this.idAbsence = id;
    }

    public String getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(String variable) {
        this.idCollaborator = variable;
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

    public AbsencesRepository getAbsenceRepository() {
        return AbsencesRepository.getInstance();
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return CollaboratorRepository.getInstance();
    }


}