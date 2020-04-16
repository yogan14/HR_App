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
    public static final String CHANNEL_1 = "HR";
    public static final String CHANNEL_2 = "IT";
    public static final String CHANNEL_3 = "Accounting";

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
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1,"Channel HR", NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("This is the HR channel");
            channel1.setShowBadge(true);


            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2,"Channel IT", NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("This is the IT channel");
            channel2.setShowBadge(true);

            NotificationChannel channel3 = new NotificationChannel(CHANNEL_3,"Channel Accounting", NotificationManager.IMPORTANCE_LOW);
            channel3.setDescription("This is the Accounting channel");
            channel3.setShowBadge(true);


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
        }
    }


}