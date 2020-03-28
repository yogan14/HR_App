package com.example.hr_app.database.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.async.collaborator.CreateCollaborator;
import com.example.hr_app.database.async.collaborator.DeleteCollaborator;
import com.example.hr_app.database.async.collaborator.UpdateCollaborator;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;


public class CollaboratorRepository {

    private static CollaboratorRepository instance;

    private CollaboratorRepository() {
    }

    public static CollaboratorRepository getInstance() {
        if (instance == null) {
            synchronized (AbsencesRepository.class) {
                if (instance == null) {
                    instance = new CollaboratorRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Collaborator> getOneCollaborator(final String email, Application application) {
        return ((BaseApp) application).getDatabase().collaboratorDao().getOneCollaborator(email);
    }

    public LiveData<List<Collaborator>> getAll(Application application) {
        return ((BaseApp) application).getDatabase().collaboratorDao().getAll();
    }

    public void insert(final Collaborator client, OnAsyncEventListener callback,
                       Application application) {
        new CreateCollaborator(application, callback).execute(client);
    }

    public void update(final Collaborator client, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCollaborator(application, callback).execute(client);
    }

    public void delete(final Collaborator client, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCollaborator(application, callback).execute(client);
    }
}
