package com.example.hr_app.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.entity.MessageEntity;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;
import com.example.hr_app.viewmodel.message.MessageListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.R;
import com.example.hr_app.ui.BaseHRActivity;
import com.example.hr_app.adapter.ChatAdapter;

import java.util.List;
import java.util.Locale;

public class ChatActivity extends BaseHRActivity {

    private DatabaseReference mReference;
    private EditText mChatInput;
    private ChatAdapter mAdapter;

    private String mUsername;
    private String userID;

    private CollaboratorViewModel cViewModel;
    private CollaboratorEntity oneCollaborator;

    private MessageListViewModel viewModel;
    private List<MessageEntity> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_chat, frameLayout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));


        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollaboratorViewModel.Factory factory = new CollaboratorViewModel.Factory(getApplication(), userID);
        cViewModel = ViewModelProviders.of(this,factory).get(CollaboratorViewModel.class);

        cViewModel.getOneCollaborator().observe(this, collaborator ->  {
            if(collaborator!=null) {
                oneCollaborator = collaborator;
                mUsername = oneCollaborator.getName();
            }
        });

        messages = new ArrayList<>();

        MessageListViewModel.Factory factory2 = new MessageListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this,factory2).get(MessageListViewModel.class);

        viewModel.gatAllMessages().observe(this, (List<MessageEntity> messages1) -> {
            if(messages1!=null){
                messages = messages1;

                mAdapter.setData(messages);
            }
        });

    }


    /**
     * setLanguage
     * Set the language from the settings
     * @param langue the language the user want
     */
    public void setLanguage(String langue){

        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }
}
