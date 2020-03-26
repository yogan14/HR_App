package com.example.hr_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.OnAsyncEventListener;
import com.example.hr_app.viewmodel.absences.OneAbsenceViewModel;

import org.w3c.dom.Text;


public class ModifyRequestAbsenceActivity extends BaseHRActivity {
    private TextView startDate;
    private TextView endDate;
    private Spinner cause;
    private Toast toast;
    private Absences absences;
    private OneAbsenceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modifyrequestabsences, frameLayout);
        int absenceID = getIntent().getIntExtra("absenceID",-1);



        int test = ((BaseApp) this.getApplication()).getTheAbsenceID();
        navigationView.setCheckedItem(position);

        cause = findViewById(R.id.cause_of_absences_spinner);
        startDate = findViewById(R.id.begining_date);
        endDate = findViewById(R.id.end_date);

        //startDate.setText(absences.getStartAbsence());

        Button update = findViewById(R.id.update_button);
        update.setOnClickListener(view -> {
            update(startDate.getText().toString(), endDate.getText().toString(),cause.getSelectedItem().toString());
            onBackPressed();
            toast.show();
        });
        toast = Toast.makeText(this, "Abscence updated", Toast.LENGTH_LONG);

        OneAbsenceViewModel.Factory factory = new OneAbsenceViewModel.Factory(getApplication(), test);
        viewModel = ViewModelProviders.of(this,factory).get(OneAbsenceViewModel.class);
        viewModel.getAbsence().observe(this, absences1 -> {
            if(absences1!=null){
                absences = absences1;
                startDate.setText(absences.getStartAbsence());
                endDate.setText(absences.getEndAbsence());
                //setSpinText(cause, cause.getSelectedItem().toString());
            }
        });

    }

    public void update(String beginning, String end, String newCause){

        if(!beginning.equals(startDate.toString())){
            absences.setStartAbsence(beginning);
        }
        if(!end.equals(endDate.toString())){
            absences.setEndAbsence(end);
        }
        if(!newCause.equals(cause.getSelectedItem().toString())){
            absences.setReason(newCause);
        }

        viewModel.updateAbsence(absences, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }
}
