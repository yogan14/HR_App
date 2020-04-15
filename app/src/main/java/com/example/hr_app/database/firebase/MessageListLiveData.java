package com.example.hr_app.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.MessageEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageListLiveData extends LiveData<List<MessageEntity>> {
    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public MessageListLiveData(DatabaseReference ref) {
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
            setValue(toMessageList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    private List<MessageEntity> toMessageList(DataSnapshot snapshot) {
        List<MessageEntity> messages = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            MessageEntity entity = childSnapshot.getValue(MessageEntity.class);
            entity.setMessageID(childSnapshot.getKey());
            messages.add(entity);
        }
        return messages;
    }
}
