package com.example.hr_app.viewmodel.collaborator;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.database.repository.CollaboratorRepository;
import com.example.hr_app.util.OnAsyncEventListener;


public class CollaboratorViewModel extends AndroidViewModel {

    private CollaboratorRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Collaborator> observableCollaborator;

    public CollaboratorViewModel(@NonNull Application application,
                                 final String email, CollaboratorRepository collaboratorRepository) {
        super(application);

        this.application = application;

        repository = collaboratorRepository;

        observableCollaborator = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCollaborator.setValue(null);

        LiveData<Collaborator> collaborator = repository.getOneCollaborator(email, application);

        // observe the changes of the client entity from the database and forward them
        observableCollaborator.addSource(collaborator, observableCollaborator::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final CollaboratorRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            repository = ((BaseApp) application).getCollaboratorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CollaboratorViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData Collaborator query so the UI can observe it.
     */
    public LiveData<Collaborator> getOneCollaborator() {
        return observableCollaborator;
    }

    public void insert(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.insert(collaborator, callback, application);
    }

    public void update(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.update(collaborator, callback, application);
    }

    public void delete(Collaborator collaborator, OnAsyncEventListener callback) {
        repository.delete(collaborator, callback, application);

    }
}
