package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;

import java.util.List;

public class PersonListActivity extends AppCompatActivity {

    private CollaboratorViewModel viewModel;
    private List<Collaborator> collaborators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlist);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

/*
        CollaboratorViewModel.Factory factory = new CollaboratorViewModel.Factory(getApplication());

        viewModel = ViewModelProviders.of(this, factory).get(CollaboratorViewModel.class);

        viewModel.getAllCollaborators().observe(
                this, steak -> {
                    if (steak != null) {
                        collaborators = steak;
                        adapter.setCollaborators(collaborators);
                    }
                }
        );*/
    }
/*
    public void addPerson(View view){
        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivity(intent);
    }

    public void modifyPerson(View view){
        Intent intent = new Intent(this, ModifyPersonActivity.class);
        startActivity(intent);
    }*/
}
