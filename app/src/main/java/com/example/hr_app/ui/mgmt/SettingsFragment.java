package com.example.hr_app.ui.mgmt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;

import java.util.Locale;

/**
 * SettingsFragment
 * Fragment to display settings
 */
public class SettingsFragment extends PreferenceFragment {
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
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
        /**
         * Listener for the changes of the language settings
         * The user will logged out automatically after selecting a language
         */
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("pref_language")){

                    setLanguage(sharedPreferences.getString("pref_language","English"));
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.pref_title));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.pref_deco_text));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> logout());

                    alertDialog.show();
                }
            }
        };
    }

    /**
     * logout the user
     */
    public void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * As soon as the user select the other language
     * a dialog will open to inform the user
     */
    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);

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
        getActivity().getResources().updateConfiguration(config,getActivity().getResources().getDisplayMetrics());

    }
}
