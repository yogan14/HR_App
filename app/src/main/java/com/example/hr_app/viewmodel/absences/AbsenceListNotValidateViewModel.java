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


public class AbsenceListNotValidateViewModel extends AndroidViewModel {
    /**
     * Declaration of the variables
     */
    private Application application;
    private AbsencesRepository repository;
    private final MediatorLiveData<List<Absences>> observableAbsencesNotValidate;

    /**
     * Constructor of the view model (ValidateAbsenceActivity and RequesAbsenceActivity)
     * @param application our application
     * @param absencesRepository the absence repository
     */
    public AbsenceListNotValidateViewModel(@NonNull Application application,
                                           AbsencesRepository absencesRepository) {
        super(application);

        this.application = application;

        repository = absencesRepository;

        observableAbsencesNotValidate = new MediatorLiveData<>();
        observableAbsencesNotValidate.setValue(null);

        LiveData<List<Absences>> AbsenceNotValidate = repository.getAbsencesNotValidate(application);
        observableAbsencesNotValidate.addSource(AbsenceNotValidate, observableAbsencesNotValidate::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final AbsencesRepository absenceRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            absenceRepository = ((BaseApp) application).getAbsenceRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new AbsenceListNotValidateViewModel(application, absenceRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<Absences>> getAbsencesNotValidate() {
        return observableAbsencesNotValidate;
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
