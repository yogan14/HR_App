package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorListLiveData extends LiveData<List<CollaboratorEntity>> {
    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CollaboratorListLiveData(DatabaseReference ref) {
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
            setValue(toCollaboratorList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    private List<CollaboratorEntity> toCollaboratorList(DataSnapshot snapshot) {
        List<CollaboratorEntity> collaborators = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CollaboratorEntity entity = childSnapshot.getValue(CollaboratorEntity.class);
            entity.setId(childSnapshot.getKey());
            collaborators.add(entity);
        }
        return collaborators;
    }
}
