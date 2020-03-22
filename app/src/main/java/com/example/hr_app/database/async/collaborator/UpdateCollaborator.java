package com.example.hr_app.database.async.collaborator;

import android.app.Application;
import android.os.AsyncTask;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.OnAsyncEventListener;


public class UpdateCollaborator extends AsyncTask<Collaborator, Void, Void> {

    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateCollaborator(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(Collaborator... params) {
        try {
            for (Collaborator collaborator : params)
                ((BaseApp) application).getDatabase().collaboratorDao()
                        .update(collaborator);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}