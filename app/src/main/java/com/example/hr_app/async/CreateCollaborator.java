package com.example.hr_app.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.hr_app.databaseClass.AppDatabase;
import com.example.hr_app.entity.Collaborator;
import com.example.hr_app.util.AsyncEventListener;

public class CreateCollaborator extends AsyncTask<Collaborator, Void, Void> {

    private AppDatabase appDatabase;
    private AsyncEventListener callback;
    private Exception exception;

    public CreateCollaborator(Context context, AsyncEventListener callback){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Collaborator... params){

        try {
            for (Collaborator collaborator : params) {
                appDatabase.collaboratorDao().insert(collaborator);
            }
        }
        catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

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
