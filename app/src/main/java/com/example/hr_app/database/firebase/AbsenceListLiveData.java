package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AbsenceListLiveData extends LiveData<List<AbsencesEntity>> {
    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public AbsenceListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {

        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {


    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toAbsenceList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    private List<AbsencesEntity> toAbsenceList(DataSnapshot snapshot) {
        List<AbsencesEntity> absences = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            AbsencesEntity entity = childSnapshot.getValue(AbsencesEntity.class);
            entity.setIdAbsence(childSnapshot.getKey());
            absences.add(entity);
        }
        return absences;
    }

}
