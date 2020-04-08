package com.example.hr_app.database.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.async.collaborator.CreateCollaborator;
import com.example.hr_app.database.async.collaborator.DeleteCollaborator;
import com.example.hr_app.database.async.collaborator.UpdateCollaborator;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;

/**
 * CollaboratorRepository
 * Repository connected with the view model
 */
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

    /**
     * getOneCollaborator
     * Get one collaborator
     * @param email - the mail of the collaborator
     * @param application - the application
     */
    public LiveData<CollaboratorEntity> getOneCollaborator(final String email, Application application) {
        return ((BaseApp) application).getDatabase().collaboratorDao().getOneCollaborator(email);
    }

    /**
     * getAll
     * Get all the collaborators order by name
     * @param application - the application
     */
    public LiveData<List<CollaboratorEntity>> getAll(Application application) {
        return ((BaseApp) application).getDatabase().collaboratorDao().getAll();
    }

    /**
     * getAll
     * Get all the collaborators order by service
     * @param application - the application
     */
    public LiveData<List<CollaboratorEntity>> getAllOrderService(Application application) {
        return ((BaseApp) application).getDatabase().collaboratorDao().getAllOrderService();
    }

    /**
     * insert
     * insert a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     * @param application - the application
     */
    public void insert(final CollaboratorEntity collaborator, OnAsyncEventListener callback,
                       Application application) {
        new CreateCollaborator(application, callback).execute(collaborator);
    }

    /**
     * update
     * update a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     * @param application - the application
     */
    public void update(final CollaboratorEntity collaborator, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCollaborator(application, callback).execute(collaborator);
    }

    /**
     * delete
     * delete a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     * @param application - the application
     */
    public void delete(final CollaboratorEntity collaborator, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCollaborator(application, callback).execute(collaborator);
    }
}
