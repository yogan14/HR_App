package com.example.hr_app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.hr_app.async.CreateAbsences;
import com.example.hr_app.async.DeleteAbsences;
import com.example.hr_app.async.UpdateAbsences;
import com.example.hr_app.databaseClass.AppDatabase;
import com.example.hr_app.entity.Absences;
import com.example.hr_app.util.AsyncEventListener;

import java.util.List;

public class AbsencesRepository {

    private static AbsencesRepository instance;

    private AbsencesRepository() {}

    // make a Singleton
    public static AbsencesRepository getInstance()
    {
        if (instance == null) {
            synchronized (AbsencesRepository.class)
            {
                if (instance == null)
                {
                    instance = new AbsencesRepository();
                }
            }
        }
        return instance;
    }

    public void insert(final Absences absences, AsyncEventListener callback, Context context){
        new CreateAbsences(context, callback).execute(absences);
    }

    public void update(final Absences absences, AsyncEventListener callback, Context context){
        new UpdateAbsences(context, callback).execute(absences);
    }

    public void delete(final Absences absences, AsyncEventListener callback, Context context){
        new DeleteAbsences(context, callback).execute(absences);
    }

    public LiveData<List<Absences>> getAbsencesForOneCollaborator(final int id, Context context){
        return AppDatabase.getAppDatabase(context).abcencesDao().getAbsencesForOneCollaborator(id);
    }

    public LiveData<List<Absences>> getAllAbsencesNotValidate(Context context){
        return AppDatabase.getAppDatabase(context).abcencesDao().getAllAbsencesNotValidate();
    }

}
