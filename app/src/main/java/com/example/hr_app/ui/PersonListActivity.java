package com.example.hr_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;
import com.example.hr_app.adapter.RecyclerAdapter;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.collaborator.CollaboratorListViewModel;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends BaseHRActivity {

    private CollaboratorListViewModel viewModel;
    private List<Collaborator> collaborators;
    private RecyclerAdapter<Collaborator> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_personlist, frameLayout);

        navigationView.setCheckedItem(position);
        setTitle("Collaborators");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        collaborators = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Intent intent = new Intent(PersonListActivity.this, )
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        CollaboratorListViewModel.Factory factory = new CollaboratorListViewModel.Factory(getApplication());

        viewModel = ViewModelProviders.of(this, factory).get(CollaboratorListViewModel.class);

        viewModel.getAllCollaborators().observe(
                this, allCollaborators -> {
                    if (allCollaborators != null) {
                        collaborators = allCollaborators;
                        adapter.setData(collaborators);
                    }
                }
        );
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
