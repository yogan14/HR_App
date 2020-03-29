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


import java.util.List;

public class CollaboratorListViewModel extends AndroidViewModel {
    /**
     * Declaration of the variables
     */
    private Application app;
    private CollaboratorRepository repository;
    private final MediatorLiveData<List<Collaborator>> observableColls;

    /**
     * Constructor for the view model (ModifyPerson and Collaborators activity)
     * @param application our application
     * @param collabo the collaborator repository
     */
    public CollaboratorListViewModel(@NonNull Application application, CollaboratorRepository collabo){
        super(application);
        this.app = application;
        repository = collabo;
        observableColls = new MediatorLiveData<>();
        observableColls.setValue(null);

        LiveData<List<Collaborator>> allCollabo = repository.getAll(application);

        observableColls.addSource(allCollabo, observableColls::setValue);
    }

    /**
     * Creator used to get the lists from the view model
     * A different view model had to be created because one
     * requires the collaborator email and this doesn't
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application appli;

        private final CollaboratorRepository cr;

        public Factory(@NonNull Application application){
            this.appli = application;
            cr =((BaseApp) application).getCollaboratorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> ModelClass){
            return (T) new CollaboratorListViewModel(appli, cr);
        }
    }

    /**
     * Method which return the list of all collaborators
     * @return the list of collaborators
     */
    public LiveData<List<Collaborator>> getAllCollabo(){
        return observableColls;
    }

}
