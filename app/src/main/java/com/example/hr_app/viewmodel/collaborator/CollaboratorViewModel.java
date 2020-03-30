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



public class CollaboratorViewModel extends AndroidViewModel {

    /**
     * Declaration of the variables
     */
    private CollaboratorRepository repository;
    private Application application;
    private final MediatorLiveData<Collaborator> observableCollaborator;

    /**
     * Constructor for the view model (ModifyPersonActivity)
     * @param application
     * @param email
     * @param collaboratorRepository
     */
    public CollaboratorViewModel(@NonNull Application application,
                                 final String email, CollaboratorRepository collaboratorRepository) {
        super(application);

        this.application = application;

        repository = collaboratorRepository;

        observableCollaborator = new MediatorLiveData<>();
        observableCollaborator.setValue(null);

        LiveData<Collaborator> collaborator = repository.getOneCollaborator(email, application);
        observableCollaborator.addSource(collaborator, observableCollaborator::setValue);
    }

    /**
     * A creator is used to inject the email into the ViewModel
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
}
