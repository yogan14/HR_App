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
import com.example.hr_app.ui.BaseHRActivity;

import java.util.List;

public class DiogoVM extends AndroidViewModel {
    private Application app;
    private CollaboratorRepository repository;


    private final MediatorLiveData<List<Collaborator>> observableColls;

    public DiogoVM(@NonNull Application application, CollaboratorRepository collabo){
        super(application);
        this.app = application;
        repository = collabo;
        observableColls = new MediatorLiveData<>();
        observableColls.setValue(null);

        LiveData<List<Collaborator>> allCollabo = repository.getAll(application);

        observableColls.addSource(allCollabo, observableColls::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application appli;

        private final String ownerId;
        private final CollaboratorRepository cr;

        public Factory(@NonNull Application application, String ownerId){
            this.appli = application;
            this.ownerId = ownerId;
            cr =((BaseApp) application).getCollaboratorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> ModelClass){
            return (T) new DiogoVM(appli, cr);
        }
    }

    public LiveData<List<Collaborator>> getAllCollabo(){
        return observableColls;
    }

}
