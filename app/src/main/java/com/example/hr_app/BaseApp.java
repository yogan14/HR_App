package com.example.hr_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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
    private String idAbsence;
    private boolean isHR;
    public static final String CHANNEL_ID = "channel1";

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
        createNotificationChannel();
    }

    public AbsencesRepository getAbsenceRepository() {
        return AbsencesRepository.getInstance();
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return CollaboratorRepository.getInstance();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is the first channel");
            channel.setShowBadge(true);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}