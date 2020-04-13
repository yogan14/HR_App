package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CollaboratorLiveData extends LiveData<CollaboratorEntity> {
    private final DatabaseReference ref;
    private final ValueListener listener = new ValueListener();

    public CollaboratorLiveData(DatabaseReference ref){
        this.ref = ref;
    }


    protected void OnActive(){
        ref.addValueEventListener(listener);
    }

    private class ValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                CollaboratorEntity entity = dataSnapshot.getValue(CollaboratorEntity.class);
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.toString());
        }
    }
}
