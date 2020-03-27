package com.example.hr_app.ui;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.hr_app.R;
import com.example.hr_app.adapter.ValidateAbsencesAdapter;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.absences.AbsenceListNotValidateViewModel;

import java.util.ArrayList;
import java.util.List;

public class ValidateAbsenceActivity extends BaseHRActivity {
    private AbsenceListNotValidateViewModel viewModel;
    private List<Absences> absencesList;
    private ValidateAbsencesAdapter adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_validate_absence_v2,frameLayout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_absences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        absencesList = new ArrayList<>();
        adapater = new ValidateAbsencesAdapter<>(/*new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }*/);

        AbsenceListNotValidateViewModel.Factory factory = new AbsenceListNotValidateViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this,factory).get(AbsenceListNotValidateViewModel.class);
        viewModel.getAbsencesNotValidate().observe(this, absences -> {
            if(absences!=null){
                absencesList = absences;
                adapater.setData(absencesList);
            }
        });

        recyclerView.setAdapter(adapater);

    }

    /*
    public void validateAccepted(View view){
        Intent intent = new Intent(this, BaseHRActivity.class);
        startActivity(intent);
    }

    public void validateDenied(View view){
        Intent intent = new Intent(this, BaseHRActivity.class);
        startActivity(intent);
    }*/
}
