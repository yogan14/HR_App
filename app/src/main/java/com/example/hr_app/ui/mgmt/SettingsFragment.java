package com.example.hr_app.ui.mgmt;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.hr_app.R;

/**
 * SettingsFragment
 * Fragment to display settings
 */
public class SettingsFragment extends PreferenceFragment {
    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML ressources
        addPreferencesFromResource(R.xml.preferences);
    }
}
