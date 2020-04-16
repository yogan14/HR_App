package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AbsenceLiveData extends LiveData<AbsencesEntity> {
    private final DatabaseReference reference;
    private final AbsenceLiveData.ValueListener listener = new AbsenceLiveData.ValueListener();
    private final String id;

    public AbsenceLiveData(DatabaseReference ref){
        reference = ref;
        id = ref.getParent().getKey();
    }


    protected void onActive(){
        reference.addValueEventListener(listener);
    }

    private class ValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AbsencesEntity entity = dataSnapshot.getValue(AbsencesEntity.class);
                entity.setIdAbsence(dataSnapshot.getKey());
                entity.setEmail(id);
                setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.toString());
        }
    }
}
