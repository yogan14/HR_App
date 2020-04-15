package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.MessageEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MessageLiveData extends LiveData<MessageEntity> {
    private final DatabaseReference ref;
    private final ValueListener listener = new ValueListener();

    public MessageLiveData(DatabaseReference ref){
        this.ref = ref;
    }


    protected void OnActive(){
        ref.addValueEventListener(listener);
    }

    private class ValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                MessageEntity entity = dataSnapshot.getValue(MessageEntity.class);
                entity.setMessageID(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.toString());
        }
    }

}
