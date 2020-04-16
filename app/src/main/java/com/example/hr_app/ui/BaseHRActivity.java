package com.example.hr_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.hr_app.BaseApp;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.CollaboratorEntity;

import com.example.hr_app.ui.mgmt.LoginActivity;
import com.example.hr_app.ui.mgmt.SettingsActivity;
import com.example.hr_app.viewmodel.collaborator.CollaboratorViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Locale;

/**
 * BaseHRActivity
 * the activity who set the burger menu
 */
public class BaseHRActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     *  Frame layout: Which is going to be used as parent layout for child activity layout.
     *  This layout is protected so that child activity can access this
     */
    protected FrameLayout frameLayout;

    protected DrawerLayout drawerLayout;

    protected NavigationView navigationView;

    protected static int position;

    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mail = FirebaseAuth.getInstance().getCurrentUser().getUid();


                //if the user is an HR member, display the HR burger menu, else, display the normal menu
                if(((BaseApp)this.getApplication()).getIsHR()) {
                    setContentView(R.layout.activity_hrmenu);
                } else {
                    setContentView(R.layout.activity_menu);
                }



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(sharedPreferences.getString("pref_language","English"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * onResume
     * State when we return in the app
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * onBackPressed
     * set the behaviour on the back button
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        BaseHRActivity.position = 0;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * create the settings menu
     * @param menu - the menu settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * onOptionItemSelected
     * set the behaviour of the settings menu
     * @param item - the item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * set the behaviour of each button in the burger
     * @param item - the item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == BaseHRActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseHRActivity.position = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);

        //set the behaviour of each button on the menu
        if (id == R.id.nav_request_absences) {
            intent = new Intent(this, RequestAbsencesActivity.class);
        } else if (id == R.id.nav_my_absences) {
            intent = new Intent(this, MyAbsencesActivity.class);
        } else if (id == R.id.nav_storage) {
            intent = new Intent(this, StorageActivity.class);
        } else if (id == R.id.nav_collaborators) {
            intent = new Intent(this, CollaboratorsActivity.class);
        } else if (id == R.id.nav_logout) {
            logout();
        }
        if (intent != null) {
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * set the behaviour of the logout button
     */
    public void logout() {
        ((BaseApp) this.getApplication()).setTheMail("Nobody connected");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
