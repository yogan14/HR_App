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


public class AbsencesRepository {

    private static AbsencesRepository instance;

    private AbsencesRepository() {

    }

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

    public LiveData<List<Absences>> getAbsencesForOneCollaborator(Application application, String email) {
        return ((BaseApp) application).getDatabase().absencesDao().getAbsencesForOneCollaborator(email);
    }

    public LiveData<List<Absences>> getAbsencesNotValidate(Application application) {
        return ((BaseApp) application).getDatabase().absencesDao().getAbsencesNotValidate();
    }

    public LiveData<Absences> getAbsence(Application application, int id){
        return ((BaseApp) application).getDatabase().absencesDao().getAbsenceByID(id);
    }

    public void insert(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new CreateAbsences(application, callback).execute(absences);
    }

    public void update(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new UpdateAbsences(application, callback).execute(absences);
    }

    public void delete(final Absences absences, OnAsyncEventListener callback,
                       Application application) {
        new DeleteAbsences(application, callback).execute(absences);
    }
}
