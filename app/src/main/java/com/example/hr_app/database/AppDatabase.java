package com.example.hr_app.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.hr_app.database.dao.AbsencesDao;
import com.example.hr_app.database.dao.CollaboratorDao;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;

import java.util.concurrent.Executors;

/**
 * AppDatabase
 * Build
 */
@Database(entities = {Absences.class, Collaborator.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "HR_DN";

    public abstract AbsencesDao absencesDao();

    public abstract CollaboratorDao collaboratorDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    /**
     * getInstance
     * AppDatabase is the object to call the db
     * @param context - actual context
     * @return INSTANCE
     */
    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * buildDatabase
     * Build the database
     * @param appContext - context of the app
     * @return database
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeDemoData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * initializeDemoData
     * Initialize the db for demo
     * @param database - database
     */
    private static void initializeDemoData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> database.runInTransaction(() -> {
            Log.i(TAG, "Wipe database.");
            database.collaboratorDao().deleteAll();
            database.absencesDao().deleteAll();

            DatabaseInitializer.populateDatabase(database);
        }));
    }

    /**
     * updateDatabaseCreated
     * update the new created db
     * @param context - context
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    /**
     * setDatabaseCreated
     * Set the new created db
     */
    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

}