package com.example.hr_app.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.hr_app.databaseClass.AppDatabase;
import com.example.hr_app.entity.Absences;
import com.example.hr_app.util.AsyncEventListener;

public class DeleteAbsences extends AsyncTask<Absences, Void, Void> {

    private AppDatabase appDatabase;
    private AsyncEventListener callback;
    private Exception exception;

    public DeleteAbsences(Context context, AsyncEventListener callback){

        appDatabase = AppDatabase.getAppDatabase(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Absences... params) {

        try {
            for (Absences absences : params) {
                appDatabase.abcencesDao().delete(absences);
            }
        }
        catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            }
            else {
                callback.onFailure(exception);
            }
        }
    }
}
