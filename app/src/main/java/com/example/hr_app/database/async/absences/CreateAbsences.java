package com.example.hr_app.database.async.absences;

import android.app.Application;
import android.os.AsyncTask;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;


public class CreateAbsences extends AsyncTask<Absences, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateAbsences(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Absences... params) {
        try {
            for (Absences absences : params)
                ((BaseApp) application).getDatabase().absencesDao()
                        .insert(absences);
        } catch (Exception e) {
            exception = e;
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