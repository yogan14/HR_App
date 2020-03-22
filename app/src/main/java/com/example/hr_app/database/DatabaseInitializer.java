package com.example.hr_app.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;


/**
 * Generates dummy data
 */
public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCollaborator(final AppDatabase db, final String name, final String service,
                                        final String email, final String password) {
        Collaborator collaborator = new Collaborator(name, service, email, password);
        db.collaboratorDao().insert(collaborator);
    }

    private static void addAbsences(final AppDatabase db, final String startAbsence, final String endAbsence,
                                    final String reason, final int idCollaborator) {
        Absences absence = new Absences(startAbsence, endAbsence, reason, idCollaborator);
        db.absencesDao().insert(absence);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.collaboratorDao().deleteAll();

        addCollaborator(db,
                "Isami Aldini", "HR", "isAldini@gmail.com", "Mezzaluna"
        );
        addCollaborator(db,
                "Akira Hayama", "IT", "akHayama@gmail.com", "Spice"
        );
        addCollaborator(db,
                "Ryō Kurokiba", "IT", "ryoKuro@gmail.com", "Shine"
        );
        addCollaborator(db,
                "Satochi Isshiki", "Accounting", "saIsshiki@gmail.com", "Apron"
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addAbsences(db,
                "16.03.2020", "17.03.2020", "sickness", 0
        );
        addAbsences(db,
                "18.03.2020", "19.03.2020", "sickness", 1
        );
        addAbsences(db,
                "19.03.2020", "21.03.2020", "vacation", 1
        );
        addAbsences(db,
                "20.04.2020", "28.04.2020", "vacation", 2
        );
        addAbsences(db,
                "13.03.2020", "17.03.2020", "paternity", 2
        );
        addAbsences(db,
                "28.03.2020", "30.03.2020", "vacation", 3
        );

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
