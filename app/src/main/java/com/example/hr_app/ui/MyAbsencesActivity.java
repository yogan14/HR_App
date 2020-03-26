package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.adapter.RecyclerDD;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.ui.CollaboratorsActivity;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.absences.AbsencesListOneCollViewModel;
import com.example.hr_app.viewmodel.collaborator.DiogoVM;

import java.util.ArrayList;
import java.util.List;

public class MyAbsencesActivity extends BaseHRActivity {
    private List<Absences> absences;
    private RecyclerDD<Absences> adapter;
    private AbsencesListOneCollViewModel viewModel;
    private static final int EDIT_ACCOUNT = 1;
    private Absences absence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_ownabsences,frameLayout);

        RecyclerView recyclerView = findViewById(R.id.absencesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String s = ((BaseApp) this.getApplication()).getTheMail();

        SharedPreferences settings = getSharedPreferences(BaseHRActivity.PREFS_NAME, 0);
        String user = settings.getString(BaseHRActivity.PREFS_NAME, null);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        navigationView.setCheckedItem(position);

        absences = new ArrayList<>();
        adapter = new RecyclerDD<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MyAbsencesActivity.this, ModifyRequestAbsenceActivity.class);
                setID(absences.get(position).getIdAbsence());
                intent.putExtra("abscenceID", absences.get(position).getIdAbsence());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        AbsencesListOneCollViewModel.Factory factory = new AbsencesListOneCollViewModel.Factory(getApplication(),s);
        viewModel = ViewModelProviders.of(this,factory).get(AbsencesListOneCollViewModel.class);
        viewModel.getAbsencesForOneCollaborator().observe(this, absences1 -> {
            if(absences1!=null){
                absences = absences1;
                adapter.setData(absences);
            }
        });

        recyclerView.setAdapter(adapter);



    }

    public void setID(int id){
        ((BaseApp) this.getApplication()).setTheAbsenceID(id);
    }

}
