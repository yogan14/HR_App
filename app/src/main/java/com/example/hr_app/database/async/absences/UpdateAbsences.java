package com.example.hr_app.database.async.absences;

import android.app.Application;
import android.os.AsyncTask;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;

/**
 * UpdateAbsences
 * Update an absence in the database
 */
public class UpdateAbsences extends AsyncTask<Absences, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateAbsences(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Absences... params) {
        try {
            for (Absences absences : params)
                ((BaseApp) application).getDatabase().absencesDao()
                        .update(absences);
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}