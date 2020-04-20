package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.CollaboratorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * The Live data of collaborator for our database
 */
public class CollaboratorLiveData extends LiveData<CollaboratorEntity> {
    private final DatabaseReference ref;
    private final CollaboratorLiveData.ValueListener listener = new CollaboratorLiveData.ValueListener();

    public CollaboratorLiveData(DatabaseReference ref){
        this.ref = ref;
    }


    protected void onActive(){
        ref.addValueEventListener(listener);
    }

    private class ValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                CollaboratorEntity entity = dataSnapshot.getValue(CollaboratorEntity.class);
            if (entity != null) {
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
