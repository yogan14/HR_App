package com.example.hr_app.database.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.hr_app.BaseApp;

import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.firebase.AbsenceListLiveData;
import com.example.hr_app.database.firebase.AbsenceLiveData;
import com.example.hr_app.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * AbsencesRepository
 * Repository connected with the view model
 */
public class AbsencesRepository {

    private static AbsencesRepository instance;


    // Singleton to get the instance
    public static AbsencesRepository getInstance() {
        if (instance == null) {
            synchronized (AbsencesRepository.class) {
                if (instance == null) {
                    instance = new AbsencesRepository();
                }
            }
        }
        return instance;
    }

    /**
     * getAbsencesForOneCollaborator
     * Get all the absences for one collaborator
     * @param collaboratorID - the mail of the collaborator
     */
    public LiveData<List<AbsencesEntity>> getAbsencesForOneCollaborator(String collaboratorID) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(collaboratorID);
        return new AbsenceListLiveData(reference, collaboratorID);
    }

    /**
     * getAbsences
     * Get one absence
     * @param absenceId - the id of the absence
     */
    public LiveData<AbsencesEntity> getAbsence(String absenceId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(absenceId);
        return new AbsenceLiveData(reference);
    }

    /**
     * insert
     * Insert an absence
     * @param absence - absence to add
     * @param callback - callback
     */
    public void insert(final AbsencesEntity absence, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key)
                .setValue(absence, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * update
     * update an absence
     * @param absence - absence to add
     * @param callback - callback
     */
    public void update(final AbsencesEntity absence, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(absence.getIdAbsence())
                .updateChildren(absence.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * delete
     * delete an absence
     * @param absence - absence to add
     * @param callback - callback
     */
    public void delete(final AbsencesEntity absence, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Absences")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(absence.getIdAbsence())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
