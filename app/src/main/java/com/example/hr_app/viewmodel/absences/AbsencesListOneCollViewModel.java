package com.example.hr_app.viewmodel.absences;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;


public class AbsencesListOneCollViewModel extends AndroidViewModel {

    private Application application;

    private AbsencesRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Absences>> observableAbsencesForOneCollaborator;

    public AbsencesListOneCollViewModel(@NonNull Application application,
                                        final String email,
                                        AbsencesRepository absencesRepository) {
        super(application);

        this.application = application;

        repository = absencesRepository;

        observableAbsencesForOneCollaborator = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableAbsencesForOneCollaborator.setValue(null);

        LiveData<List<Absences>> AbsencesOneColl = repository.getAbsencesForOneCollaborator(application, email);

        // observe the changes of the entities from the database and forward them
        observableAbsencesForOneCollaborator.addSource(AbsencesOneColl, observableAbsencesForOneCollaborator::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final AbsencesRepository absenceRepository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            absenceRepository = ((BaseApp) application).getAbsenceRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AbsencesListOneCollViewModel(application, email, absenceRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<Absences>> getAbsencesForOneCollaborator() {
        return observableAbsencesForOneCollaborator;
    }

    public void delete(Absences absences, OnAsyncEventListener callback) {
        repository.delete(absences, callback, application);
    }

    public void insert(Absences absences, OnAsyncEventListener callback) {
        repository.insert(absences, callback, application);
    }

    public void update(Absences absences, OnAsyncEventListener callback) {
        repository.update(absences, callback, application);
    }
}
