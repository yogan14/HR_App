package com.example.hr_app.database.repository;


import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.firebase.CollaboratorListLiveData;
import com.example.hr_app.database.firebase.CollaboratorLiveData;
import com.example.hr_app.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
            synchronized (CollaboratorRepository.class) {
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
     * Get all the collaborators
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
     * @param collaborator - collaborator to update
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
     * @param collaborator - collaborator to delete
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

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }
}
