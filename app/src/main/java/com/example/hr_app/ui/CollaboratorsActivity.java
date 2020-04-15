package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.adapter.ListAdapter;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class CollaboratorsActivity extends BaseHRActivity {

    /**
     * Declaration of the variables
     */
    private List<CollaboratorEntity> collaborators;
    private ListAdapter<CollaboratorEntity> adapter;
    private CollaboratorListViewModel viewModel;
    private FloatingActionButton addButton;

    /**
     * onCreate
     * On the creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        /**
         * Get the layout from the xml file
         */
        getLayoutInflater().inflate(R.layout.activity_collaborators, frameLayout);
        setDisplay();

    }

    /**
     * onResume
     * State when we return in the app
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        setDisplay();
    }

    public void setDisplay(){
        /**
         * Creation of the button and the recycler view and set the relative settings
         */
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> add());

        RecyclerView recyclerView = findViewById(R.id.collaboratorsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /**
         * Creation of the list of collaborators and the relative adapter
         */
        collaborators = new ArrayList<>();
        adapter = new ListAdapter<>(new RecyclerViewItemClickListener() {
            /**
             * onItemClick
             * Action according the collaborator selected
             * @param v the current view
             * @param position the position in the list
             */
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(CollaboratorsActivity.this, ModifyPersonActivity.class);
                ((BaseApp) getApplication()).setMailCollaborator(collaborators.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        /**
         * Filling the list
         */
        CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this,factory).get(CollaboratorListViewModel.class);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPrefs.getBoolean("pref_order", false)){
            viewModel.getAllCollabo().observe(this, (List<CollaboratorEntity> collaborators1) -> {
                if(collaborators1!=null){
                    collaborators = collaborators1;

                    //sort collaborator list by service
                    Comparator<CollaboratorEntity> compareByService = (CollaboratorEntity c1, CollaboratorEntity c2) -> c1.getService().compareTo(c2.getService());
                    Collections.sort(collaborators, compareByService);

                    adapter.setData(collaborators);
                }
            });
        } else {
            viewModel.getAllCollabo().observe(this, (List<CollaboratorEntity> collaborators1) -> {
                if(collaborators1!=null){
                    collaborators = collaborators1;

                    //sort collaborator list by name
                    Comparator<CollaboratorEntity> compareByName = (CollaboratorEntity c1, CollaboratorEntity c2) -> c1.getName().compareTo(c2.getName());
                    Collections.sort(collaborators, compareByName);

                    adapter.setData(collaborators);
                }
            });
        }




        recyclerView.setAdapter(adapter);
    }

    /**
     * Method which will sent to the creation of a collaborator activity
     */
    public void add() {

        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivity(intent);
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
