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
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import com.example.hr_app.viewmodel.absences.AbsencesListOneCollViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * MyAbsencesActivity
 * the users can see all of their absences
 */
public class MyAbsencesActivity extends BaseHRActivity {
    /**
     * Declaration of variables
     */
    private List<AbsencesEntity> absences;
    private ListAdapter<AbsencesEntity> adapter;
    private AbsencesListOneCollViewModel viewModel;


    /**
     * onCreate
     * On the creation of the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the layout from the xml file
        getLayoutInflater().inflate(R.layout.activity_ownabsences,frameLayout);
        setDisplay();
    }

    /**
     * When the activity resume
     */
    @Override
    protected void onResume(){
        super.onResume();
        setDisplay();
    }

    /**
     * set the display of the activity
     */
    public void setDisplay(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        //Creation of the recycler view and the relative layout

        RecyclerView recyclerView = findViewById(R.id.absencesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        navigationView.setCheckedItem(position);

        //Session to get the mail of the connected collaborator
        String s = ((BaseApp) this.getApplication()).getTheMail();

        //Creation of the list and the relative adapter
        absences = new ArrayList<>();
        adapter = new ListAdapter<>(new RecyclerViewItemClickListener() {

            /**
             * onItemClick
             * Action according to the absence selected
             * @param v the current view
             * @param position the position in the list
             */
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MyAbsencesActivity.this, ModifyRequestAbsenceActivity.class);
                setID(absences.get(position).getIdAbsence());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        //Filling the list
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

    /**
     * Method that allows to set the id in the "Session"
     * @param id the absence ID
     */
    public void setID(int id){
        ((BaseApp) this.getApplication()).setTheID(id);
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
