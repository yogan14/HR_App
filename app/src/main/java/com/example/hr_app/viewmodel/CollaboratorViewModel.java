package com.example.hr_app.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.hr_app.entity.Collaborator;
import com.example.hr_app.repository.CollaboratorRepository;
import com.example.hr_app.util.AsyncEventListener;

import java.util.List;

public class CollaboratorViewModel extends AndroidViewModel {

    private CollaboratorRepository repository ;
    private LiveData<List<Collaborator>> allCollaborators;
    private Context appContext;


    public CollaboratorViewModel(@NonNull Application application, CollaboratorRepository repository) {
        super(application);

        appContext = application.getApplicationContext();

        this.repository = repository;
        this.allCollaborators = repository.getAllCollaborators(appContext);

    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application app;

        private final CollaboratorRepository collaboratorRepository;

        public Factory(@NonNull Application app) {
            this.app = app;
            collaboratorRepository = CollaboratorRepository.getInstance();
        }
    }





    public void insert(Collaborator collaborator, AsyncEventListener callback){
        repository.insert(collaborator, callback, appContext);
    }

    public void update(Collaborator collaborator, AsyncEventListener callback){
        repository.update(collaborator, callback, appContext);
    }

    public void delete(Collaborator collaborator, AsyncEventListener callback){
        repository.delete(collaborator, callback, appContext);
    }

    public LiveData<List<Collaborator>> getAllCollaborators(){
        repository.getAllCollaborators(appContext);
        return allCollaborators;
    }


}
