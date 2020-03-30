package com.example.hr_app.database.repository;

import android.app.ActionBar;
import android.app.Application;
import androidx.lifecycle.LiveData;
import android.util.Pair;

import com.example.hr_app.BaseApp;

import com.example.hr_app.database.async.absences.CreateAbsences;
import com.example.hr_app.database.async.absences.DeleteAbsences;
import com.example.hr_app.database.async.absences.UpdateAbsences;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;

/**
 * AbsencesRepository
 * Repository connected with the view model
 */
public class AbsencesRepository {

    private static AbsencesRepository instance;

    private AbsencesRepository() {

    }

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
     * @param application - the application
     * @param email - the mail of the collaborator
     */
    public LiveData<List<Absences>> getAbsencesForOneCollaborator(Application application, String email) {
        return ((BaseApp) application).getDatabase().absencesDao().getAbsencesForOneCollaborator(email);
    }
    /**
     * getAbsencesNotValidate
     * Get all the absences not validate by a HR
     * @param application - the application
     */
    public LiveData<List<Absences>> getAbsencesNotValidate(Application application) {
        return ((BaseApp) application).getDatabase().absencesDao().getAbsencesNotValidate(true);
    }

    /**
     * getAbsencesNotValidate
     * Get one absence
     * @param application - the application
     * @param id - the id of the absence
     */
    public LiveData<Absences> getAbsence(Application application, int id){
        return ((BaseApp) application).getDatabase().absencesDao().getAbsenceByID(id);
    }

    /**
     * insert
     * Insert an absence
     * @param absences - absence to add
     * @param callback - callback
     * @param application - the application
     */
    public void insert(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new CreateAbsences(application, callback).execute(absences);
    }

    /**
     * update
     * update an absence
     * @param absences - absence to add
     * @param callback - callback
     * @param application - the application
     */
    public void update(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new UpdateAbsences(application, callback).execute(absences);
    }

    /**
     * delete
     * delete an absence
     * @param absences - absence to add
     * @param callback - callback
     * @param application - the application
     */
    public void delete(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new DeleteAbsences(application, callback).execute(absences);
    }
}
