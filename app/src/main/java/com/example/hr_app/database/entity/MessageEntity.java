package com.example.hr_app.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class MessageEntity implements Comparable {

    private String messageID;
    private String senderName;
    private String message;

    public MessageEntity() {
    }

    public MessageEntity(String senderName, String message, long timestamp) {
        this.senderName = senderName;
        this.message = message;
    }

    @Exclude
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MessageEntity)) return false;
        MessageEntity o = (MessageEntity) obj;
        return o.getMessageID() == this.getMessageID();
    }

    @Override
    public String toString() {
        return senderName + " : " + message;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("senderName", senderName);

        return result;
    }
}
