package com.example.hr_app.viewmodel.collaborator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.database.repository.CollaboratorRepository;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;


public class CollaboratorListViewModel extends AndroidViewModel {

    private Application application;

    private CollaboratorRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Collaborator>> observableAllCollaborators;

    public CollaboratorListViewModel(@NonNull Application application,
                                     CollaboratorRepository collaboratorRepository) {
        super(application);

        this.application = application;

        repository = collaboratorRepository;

        observableAllCollaborators = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableAllCollaborators.setValue(null);

        LiveData<List<Collaborator>> allCollabo = repository.getAll(application);

        // observe the changes of the entities from the database and forward them
        observableAllCollaborators.addSource(allCollabo, observableAllCollaborators::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CollaboratorRepository collaboratorRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            collaboratorRepository = ((BaseApp) application).getCollaboratorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CollaboratorListViewModel(application, collaboratorRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<Collaborator>> getAllCollaborators() {
        return observableAllCollaborators;
    }

    public void delete(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.delete(collaborator, callback, application);
    }

    public void insert(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.insert(collaborator, callback, application);
    }

    public void update(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.update(collaborator, callback, application);
    }

}
