package com.example.hr_app.viewmodel.collaborator;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.repository.CollaboratorRepository;



public class CollaboratorViewModel extends AndroidViewModel {

    /**
     * Declaration of the variables
     */
    private CollaboratorRepository repository;
    private final MediatorLiveData<CollaboratorEntity> observableCollaborator;

    /**
     * Constructor for the view model (ModifyPersonActivity)
     * @param application - the app
     * @param collaboratorId - the ID of the collaborator
     * @param collaboratorRepository - The repository
     */
    public CollaboratorViewModel(@NonNull Application application,
                                 final String collaboratorId, CollaboratorRepository collaboratorRepository) {
        super(application);

        repository = collaboratorRepository;

        observableCollaborator = new MediatorLiveData<>();
        observableCollaborator.setValue(null);


        LiveData<CollaboratorEntity> collaborator = repository.getOneCollaborator(collaboratorId);
        observableCollaborator.addSource(collaborator, observableCollaborator::setValue);
    }

    /**
     * A creator is used to inject the email into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String collaboratorId;

        private final CollaboratorRepository repository;


        public Factory(@NonNull Application application, String collaboratorId) {
            this.application = application;
            this.collaboratorId = collaboratorId;

            repository = ((BaseApp) application).getCollaboratorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CollaboratorViewModel(application, collaboratorId, repository);
        }
    }

    /**
     * Expose the LiveData Collaborator query so the UI can observe it.
     */
    public LiveData<CollaboratorEntity> getOneCollaborator() {
        return observableCollaborator;
    }
}
