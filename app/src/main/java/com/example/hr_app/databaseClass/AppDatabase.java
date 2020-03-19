package com.example.hr_app.databaseClass;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hr_app.dao.AbcencesDao;
import com.example.hr_app.dao.CollaboratorDao;
import com.example.hr_app.entity.Absences;
import com.example.hr_app.entity.Collaborator;

import java.util.concurrent.Executors;

@Database(entities = {Absences.class, Collaborator.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String dbName = "dbDN";

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    private static AppDatabase instance;

    private static final Object LOCK = new Object();

    public abstract AbcencesDao abcencesDao();
    public abstract CollaboratorDao collaboratorDao();


    public synchronized static AppDatabase getAppDatabase(Context context)
    {
        if(instance == null)
        {
            synchronized (LOCK)
            {
                if(instance == null)
                {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDatabase buildDatabase(final Context context) {

        return Room.databaseBuilder(context, AppDatabase.class, dbName).addCallback(new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase database = AppDatabase.getAppDatabase(context);
                    DbInitializer.populateDatabase(database);
                    // notify that the database was created and it's ready to be used
                    database.setDatabaseCreated();
                });
            }
        }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if(context.getDatabasePath(dbName).exists()){
            setDatabaseCreated();
        }
    }


    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }

    public static void initializeDemoDate(final AppDatabase database){
        Executors.newSingleThreadExecutor().execute(() ->
        {
            database.runInTransaction(() -> {
                DbInitializer.populateDatabase(database);
            });
        });
    }
}
