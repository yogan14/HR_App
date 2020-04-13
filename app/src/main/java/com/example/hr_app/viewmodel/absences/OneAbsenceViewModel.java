package com.example.hr_app.viewmodel.absences;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.repository.AbsencesRepository;


public class OneAbsenceViewModel extends AndroidViewModel {
    /**
     * Declaration of the variables
     */
    private Application application;
    private AbsencesRepository repository;
    private final MediatorLiveData<AbsencesEntity> observableAbsence;

    /**
     * Constructor of the view model (ModifyRequestActivity)
     * @param application our application
     * @param absenceID the ID of the absence
     * @param absencesRepository the absence repository
     */
    public OneAbsenceViewModel(@NonNull Application application, final
    String absenceID, AbsencesRepository absencesRepository){
        super(application);
        this.application = application;
        repository = absencesRepository;
        observableAbsence = new MediatorLiveData<>();
        observableAbsence.setValue(null);

        LiveData<AbsencesEntity> absence = repository.getAbsence(absenceID);

        observableAbsence.addSource(absence, observableAbsence::setValue);
    }

    /**
     * A creator is used to inject the absence id into the view model
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final String absenceID;
        private final AbsencesRepository repository;

        public Factory(@NonNull Application application, String absenceID){
            this.application = application;
            this.absenceID = absenceID;
            repository =((BaseApp) application).getAbsenceRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass){
            return (T) new OneAbsenceViewModel(application, absenceID, repository);
        }
    }

    /**
     * Method which will return the absence
     * @return the specific absence
     */
    public LiveData<AbsencesEntity> getAbsence(){
        return observableAbsence;
    }

}
