package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.adapter.RecyclerDD;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.collaborator.DiogoVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class CollaboratorsActivity extends BaseHRActivity {

    private List<Collaborator> collaborators;
    private RecyclerDD<Collaborator> adapter;
    private DiogoVM viewModel;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_collaborators, frameLayout);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> add());

        RecyclerView recyclerView = findViewById(R.id.collaboratorsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences settings = getSharedPreferences(BaseHRActivity.PREFS_NAME, 0);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        collaborators = new ArrayList<>();
        adapter = new RecyclerDD<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(CollaboratorsActivity.this, ModifyPersonActivity.class);
                ((BaseApp) getApplication()).setMailCollaborator(collaborators.get(position).getEmail());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        DiogoVM.Factory factory = new DiogoVM.Factory(getApplication());
        viewModel = ViewModelProviders.of(this,factory).get(DiogoVM.class);
        viewModel.getAllCollabo().observe(this, (List<Collaborator> collaborators1) -> {
            if(collaborators1!=null){
                collaborators = collaborators1;
                adapter.setData(collaborators);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void add() {
        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivity(intent);
    }
}
