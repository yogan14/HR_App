package com.example.hr_app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.hr_app.async.CreateCollaborator;
import com.example.hr_app.async.DeleteCollaborator;
import com.example.hr_app.async.UpdateCollaborator;
import com.example.hr_app.databaseClass.AppDatabase;
import com.example.hr_app.entity.Collaborator;
import com.example.hr_app.util.AsyncEventListener;

import java.util.List;

public class CollaboratorRepository {

    private static CollaboratorRepository instance;

    private CollaboratorRepository() {}

    // make a Singleton
    public static CollaboratorRepository getInstance()
    {
        if (instance == null) {
            synchronized (CollaboratorRepository.class)
            {
                if (instance == null)
                {
                    instance = new CollaboratorRepository();
                }
            }
        }
        return instance;
    }

    public void insert(final Collaborator absences, AsyncEventListener callback, Context context){
        new CreateCollaborator(context, callback).execute(absences);
    }

    public void update(final Collaborator absences, AsyncEventListener callback, Context context){
        new UpdateCollaborator(context, callback).execute(absences);
    }

    public void delete(final Collaborator absences, AsyncEventListener callback, Context context){
        new DeleteCollaborator(context, callback).execute(absences);
    }

    public LiveData<List<Collaborator>> getAllCollaborators(Context context){
        return AppDatabase.getAppDatabase(context).collaboratorDao().getAllCollaborators();
    }

}
