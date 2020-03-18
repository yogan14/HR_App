package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hr_app.Adapter.CollaboratorAdapter;
import com.example.hr_app.R;
import com.example.hr_app.entity.Collaborator;
import com.example.hr_app.viewmodel.CollaboratorViewModel;

import java.util.List;

public class PersonListActivity extends AppCompatActivity {

    private CollaboratorViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlist);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CollaboratorAdapter adapter = new CollaboratorAdapter();
        recyclerView.setAdapter(adapter);

        CollaboratorViewModel.Factory factory = new CollaboratorViewModel.Factory(getApplication());

        viewModel = ViewModelProviders.of(this, factory).get(CollaboratorViewModel.class);
        viewModel.getAllCollaborators().observe(this, new Observer<List<Collaborator>>() {
            @Override
            public void onChanged(List<Collaborator> collaborators) {
                adapter.setCollaborators(collaborators);
            }
        });
    }

    public void addPerson(View view){
        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivity(intent);
    }

    public void modifyPerson(View view){
        Intent intent = new Intent(this, ModifyPersonActivity.class);
        startActivity(intent);
    }
}
