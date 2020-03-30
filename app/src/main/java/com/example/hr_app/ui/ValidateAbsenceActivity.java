package com.example.hr_app.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.hr_app.R;
import com.example.hr_app.adapter.ValidateAbsencesAdapter;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.AbsenceListNotValidateViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ValidateAbsenceActivity extends BaseHRActivity {
    /**
     * Declaration of variables
     */
    private AbsenceListNotValidateViewModel viewModel;
    private List<Absences> absencesList;
    private ValidateAbsencesAdapter adapter;
    private Absences absence;


    /**
     * on create
     * On the creation of the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Get the layout from the xml file
         */
        getLayoutInflater().inflate(R.layout.activity_validate_absence_v2, frameLayout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language", "English"));

        /**
         * Get the recyclerView and set in a vertical layout
         */
        RecyclerView recyclerView = findViewById(R.id.recycler_view_absences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /**
         * Creation of the list of absences and the relative adapter
         */
        absencesList = new ArrayList<>();
        adapter = new ValidateAbsencesAdapter<>();

        /**
         * Filling the list
         */
        AbsenceListNotValidateViewModel.Factory factory = new AbsenceListNotValidateViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(AbsenceListNotValidateViewModel.class);
        viewModel.getAbsencesNotValidate().observe(this, absences -> {
            if (absences != null) {
                absencesList = absences;
                adapter.setData(absencesList);
            }
        });

        /**
         * Creation of the swipe feature
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * onSwiped
             * Actions carried out according to the direction
             * @param viewHolder the viewHolder from the View Model
             * @param direction left or right
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                /**
                 * Swipping left delete the absence
                 */
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.delete((Absences) adapter.getAbsenceAt(viewHolder.getAdapterPosition()), new OnAsyncEventListener() {
                        /**
                         * A toast will pop up if the absence is correctly deleted
                         */
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ValidateAbsenceActivity.this, (getString(R.string.absence_refused)), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                    /**
                     * Swipping right accept the absence
                     */
                } else {
                    /**
                     * Get the absence according to the ID
                     */
                    absence = (Absences) adapter.getAbsenceAt(viewHolder.getAdapterPosition());
                    absence.setValidate(true);
                    viewModel.update(absence, new OnAsyncEventListener() {
                        /**
                         * A toast will pop up if the absence is correctly updated
                         */
                        @Override
                        public void onSuccess() {

                            Toast.makeText(ValidateAbsenceActivity.this, (getString(R.string.absence_accepted)), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                }


            }
        }).attachToRecyclerView(recyclerView); //link the swipe feature to the recycler view


        /**
         * Link the adapter to the recycler view
         */
        recyclerView.setAdapter(adapter);

    }

    public void setLanguage(String langue) {
        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
