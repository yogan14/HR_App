package com.example.hr_app.database.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.hr_app.BaseApp;

import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.firebase.AbsenceListLiveData;
import com.example.hr_app.database.firebase.AbsenceLiveData;
import com.example.hr_app.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

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
     * @param email - the mail of the collaborator
     */
    public LiveData<List<AbsencesEntity>> getAbsencesForOneCollaborator(String email) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(email)
                .child("accounts");
        return new AbsenceListLiveData(reference, email);
    }

    /**
     * getAbsences
     * Get one absence
     * @param accountId - the id of the absence
     */
    public LiveData<AbsencesEntity> getAbsence(String accountId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("accounts")
                .child(accountId);
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
                .getReference("clients")
                .child(absence.getEmail())
                .child("accounts");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(absence.getEmail())
                .child("accounts")
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
                .getReference("clients")
                .child(absence.getEmail())
                .child("accounts")
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
     * @param absences - absence to add
     * @param callback - callback
     * @param application - the application
     */
    public void delete(final AbsencesEntity absences, OnAsyncEventListener callback,
                       Application application) {
        new DeleteAbsences(application, callback).execute(absences);
    }
}
