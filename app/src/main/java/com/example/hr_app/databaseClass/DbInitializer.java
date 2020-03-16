package com.example.hr_app.databaseClass;

import android.os.AsyncTask;

import com.example.hr_app.entity.Absences;
import com.example.hr_app.entity.Collaborator;

public class DbInitializer {

    private static final String tag = "DbInitializer";

    public static void populateDatabase(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();

    }

    private static void addCollaborator (final AppDatabase db, final String name, final String service, final String email, final String password) {

        Collaborator collaborator = new Collaborator(name, service, email, password);
        db.collaboratorDao().insert(collaborator);

    }

    private static void addAbcence (final AppDatabase db, final String startAbsence, final String endAbsence, final String reason, int idCollaborator) {

        Absences absences = new Absences(startAbsence, endAbsence, reason, idCollaborator);
        db.abcencesDao().insert(absences);

    }



    private static void StartWithTestData(AppDatabase db) {

        addCollaborator(db, "Isami Aldini", "HR", "isAldini@gmail.com", "Mezzaluna");
        addCollaborator(db, "Akira Hayama", "IT", "akHayama@gmail.com", "Spice");
        addCollaborator(db, "Ryō Kurokiba", "IT", "ryoKuro@gmail.com", "Shine ");
        addCollaborator(db, "Satochi Isshiki", "Accounting", "saIsshiki@gmail.com", "Apron");

        addAbcence(db, "16.03.2020", "17.03.2020", "sickness", 0);
        addAbcence(db, "18.03.2020", "19.03.2020", "dentist", 1);
        addAbcence(db, "19.03.2020", "21.03.2020", "vacation", 1);
        addAbcence(db, "20.04.2020", "28.04.2020", "sickness", 2);
        addAbcence(db, "13.03.2020", "17.03.2020", "paternity", 2);
        addAbcence(db, "28.03.2020", "30.03.2020", "vacation", 3);


    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            StartWithTestData(database);
            return null;
        }
    }


}
