package com.example.hr_app.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.firebase.CollaboratorListLiveData;
import com.example.hr_app.database.firebase.CollaboratorLiveData;
import com.example.hr_app.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
     * @param collaboratorId - the mail of the collaborator
     */
    public LiveData<CollaboratorEntity> getOneCollaborator(final String collaboratorId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Collaborators")
                .child(collaboratorId);
        return new CollaboratorLiveData(reference);
    }

    /**
     * getAll
     * Get all the collaborators order by name
     */
    public LiveData<List<CollaboratorEntity>> getAll() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Collaborators");
        return new CollaboratorListLiveData(reference);
    }

    /**
     * insert
     * insert a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     */
    public void insert(final CollaboratorEntity collaborator, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Collaborators");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("Collaborators")
                .child(key)
                .setValue(collaborator, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * update
     * update a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     */
    public void update(final CollaboratorEntity collaborator, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Collaborators")
                .child(collaborator.getId())
                .updateChildren(collaborator.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * delete
     * delete a collaborator
     * @param collaborator - collaborator to add
     * @param callback - callback
     */
    public void delete(final CollaboratorEntity collaborator, OnAsyncEventListener callback) {
            FirebaseDatabase.getInstance()
                    .getReference("Collaborators")
                    .child(collaborator.getId())
                    .removeValue((databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
    }
}
