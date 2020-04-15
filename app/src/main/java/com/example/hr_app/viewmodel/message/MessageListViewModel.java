package com.example.hr_app.viewmodel.message;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.hr_app.BaseApp;
import com.example.hr_app.database.entity.MessageEntity;
import com.example.hr_app.database.repository.MessageRepository;
import com.example.hr_app.util.OnAsyncEventListener;

import java.util.List;

public class MessageListViewModel extends AndroidViewModel {
    /**
     * Declaration of the variables
     */
    private Application app;
    private MessageRepository repository;
    private final MediatorLiveData<List<MessageEntity>> observableMessage;

    /**
     * Constructor for the view model (chat activity)
     * @param application our application
     * @param messageRepository the message repository
     */
    public MessageListViewModel(@NonNull Application application, MessageRepository messageRepository){
        super(application);
        this.app = application;
        repository = messageRepository;

        observableMessage = new MediatorLiveData<>();
        observableMessage.setValue(null);

        LiveData<List<MessageEntity>> allMessages = repository.getAllMessages();

        observableMessage.addSource(allMessages, observableMessage::setValue);

    }

    /**
     * Creator used to get the lists from the view model
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application appli;

        private final MessageRepository cr;

        public Factory(@NonNull Application application){
            this.appli = application;
            cr =((BaseApp) application).getMessageRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> ModelClass){
            return (T) new MessageListViewModel(appli, cr);
        }
    }

    /**
     * Method which return the list of all collaborators
     * @return the list of collaborators
     */
    public LiveData<List<MessageEntity>> gatAllMessages(){
        return observableMessage;
    }

    public void insert(MessageEntity message, OnAsyncEventListener callback) {
        repository.insert(message, callback);
    }

    public void update(MessageEntity message, OnAsyncEventListener callback) {
        repository.update(message, callback);
    }

    public void delete(MessageEntity message, OnAsyncEventListener callback) {
        repository.delete(message, callback);
    }
}
