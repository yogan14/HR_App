package com.example.hr_app.database.repository;

import androidx.lifecycle.LiveData;

import com.example.hr_app.database.entity.MessageEntity;
import com.example.hr_app.database.firebase.MessageListLiveData;
import com.example.hr_app.database.firebase.MessageLiveData;
import com.example.hr_app.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageRepository {

    private static MessageRepository instance;

    private MessageRepository() {
    }

    public static MessageRepository getInstance() {
        if (instance == null) {
            synchronized (MessageRepository.class) {
                if (instance == null) {
                    instance = new MessageRepository();
                }
            }
        }
        return instance;
    }

    /**
     * getOneMessage
     * Get one message
     * @param messageID - the message
     */
    public LiveData<MessageEntity> getOneMessage(final String messageID) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Chat")
                .child(messageID);
        return new MessageLiveData(reference);
    }

    /**
     * getAll
     * Get all the messages
     */
    public LiveData<List<MessageEntity>> getAllMessages() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Chat");
        return new MessageListLiveData(reference);
    }

    /**
     * insert
     * insert a message
     * @param message - the message to add
     * @param callback - callback
     */
    public void insert(final MessageEntity message, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Chat");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("Chat")
                .child(key)
                .setValue(message, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * update
     * update a message
     * @param message - message to update
     * @param callback - callback
     */
    public void update(final MessageEntity message, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Chat")
                .child(message.getMessageID())
                .updateChildren(message.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * delete
     * delete a message
     * @param message - message to delete
     * @param callback - callback
     */
    public void delete(final MessageEntity message, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Chat")
                .child(message.getMessageID())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
