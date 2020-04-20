package com.example.hr_app.viewmodel.absences;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.repository.AbsencesRepository;
import com.example.hr_app.util.OnAsyncEventListener;

public class IUDAbsencesViewModel extends AndroidViewModel {
    /**
     * Declaration of the variables
     */
    private Application application;
    private AbsencesRepository repository;

    /**
     * Constructor of the view model (ValidateAbsenceActivity and RequesAbsenceActivity)
     * @param application our application
     * @param absencesRepository the absence repository
     */
    private IUDAbsencesViewModel(@NonNull Application application,
                                           AbsencesRepository absencesRepository) {
        super(application);

        this.application = application;

        repository = absencesRepository;
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

            return (T) new IUDAbsencesViewModel(application, absenceRepository);
        }
    }

    /**
     * Method which insert absence in the db
     * @param absences - the absence to add
     * @param callback - the callback
     */
    public void insert(AbsencesEntity absences, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getAbsenceRepository().insert(absences, callback);
    }

    /**
     * Method which update absence in the db
     * @param absences - the absence to update
     * @param callback - the callback
     */
    public void update(AbsencesEntity absences, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getAbsenceRepository().update(absences, callback);
    }

    /**
     * Method which delete the absence in the db
     * @param absences - the absence to delete
     * @param callback - the callback
     */
    public void delete(AbsencesEntity absences, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getAbsenceRepository().delete(absences, callback);
    }
}
