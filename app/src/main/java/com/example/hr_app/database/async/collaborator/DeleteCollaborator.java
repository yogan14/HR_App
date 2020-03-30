package com.example.hr_app.database.async.collaborator;

import android.app.Application;
import android.os.AsyncTask;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.OnAsyncEventListener;

/**
 * DeleteCollaborator
 * Delete a Collaborator in the database
 */
public class DeleteCollaborator extends AsyncTask<Collaborator, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteCollaborator(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Collaborator... params) {
        try {
            for (Collaborator collaborator : params)
                ((BaseApp) application).getDatabase().collaboratorDao()
                        .delete(collaborator);
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