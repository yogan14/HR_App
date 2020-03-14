package com.example.hr_app.DatabaseClass;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hr_app.dao.AbcencesDao;
import com.example.hr_app.dao.CollaboratorDao;
import com.example.hr_app.entity.Absences;
import com.example.hr_app.entity.Collaborator;

@Database(entities = {Absences.class, Collaborator.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

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
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_databaseDN")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

}
