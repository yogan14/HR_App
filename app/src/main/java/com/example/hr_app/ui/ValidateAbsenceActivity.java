package com.example.hr_app.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hr_app.R;
import com.example.hr_app.adapter.ValidateAbsencesAdapter;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.absences.AbsenceListNotValidateViewModel;
import com.example.hr_app.viewmodel.absences.OneAbsenceViewModel;

import java.util.ArrayList;
import java.util.List;

public class ValidateAbsenceActivity extends BaseHRActivity {
    private AbsenceListNotValidateViewModel viewModel;

    private List<Absences> absencesList;
    private ValidateAbsencesAdapter adapter;
    private Absences absence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_validate_absence_v2,frameLayout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_absences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        absencesList = new ArrayList<>();
        adapter = new ValidateAbsencesAdapter<>(/*new RecyclerViewItemClickListener() {
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
                adapter.setData(absencesList);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.LEFT){
                    viewModel.delete((Absences)adapter.getAbsenceAt(viewHolder.getAdapterPosition()), new OnAsyncEventListener() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(ValidateAbsenceActivity.this, "Absence refused", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                } else {
                    absence = (Absences) adapter.getAbsenceAt(viewHolder.getAdapterPosition());
                    absence.setValidate(true);
                    viewModel.update(absence, new OnAsyncEventListener() {
                       @Override
                       public void onSuccess() {
                           Toast.makeText(ValidateAbsenceActivity.this, "Absence accepted", Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onFailure(Exception e) {

                       }
                   });
                }


            }
        }).attachToRecyclerView(recyclerView);

        viewModel.getAbsencesNotValidate().observe(this, new Observer<List<Absences>>() {
            @Override
            public void onChanged(List<Absences> absences) {

            }
        });

        recyclerView.setAdapter(adapter);

    }
}
