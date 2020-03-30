package com.example.hr_app.ui.mgmt;

import android.app.Activity;
import android.os.Bundle;

/**
 * SettingsActivity
 * Activity to display settings
 */
public class SettingsActivity extends Activity
{
    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment())
                .commit();
    }
}