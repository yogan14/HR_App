package com.example.hr_app.viewmodel.absences;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.repository.AbsencesRepository;
import java.util.List;


public class AbsencesListOneCollViewModel extends AndroidViewModel {

    /**
     * Declaration of the variables
     */
    private Application application;
    private AbsencesRepository repository;
    private final MediatorLiveData<List<AbsencesEntity>> observableAbsencesForOneCollaborator;

    /**
     * Constructor of the view model (MyAbsenceActivity)
     * @param application our application
     * @param email the email of the connected collaborator
     * @param absencesRepository the absence repository
     */
    public AbsencesListOneCollViewModel(@NonNull Application application,
                                        final String email,
                                        AbsencesRepository absencesRepository) {
        super(application);

        this.application = application;

        repository = absencesRepository;

        observableAbsencesForOneCollaborator = new MediatorLiveData<>();
        observableAbsencesForOneCollaborator.setValue(null);

        LiveData<List<AbsencesEntity>> AbsencesOneColl = repository.getAbsencesForOneCollaborator(application, email);


        observableAbsencesForOneCollaborator.addSource(AbsencesOneColl, observableAbsencesForOneCollaborator::setValue);
    }

    /**
     * A creator is used to inject the email into the view model
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
     * Method which will return all absences for the current collaborator
     * @return the list of absences
     */
    public LiveData<List<AbsencesEntity>> getAbsencesForOneCollaborator() {
        return observableAbsencesForOneCollaborator;
    }
}
