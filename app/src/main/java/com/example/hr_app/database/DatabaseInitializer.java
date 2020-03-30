package com.example.hr_app.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;


/**
 * DatabaseInitializer
 * Initialize the db
 */
public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    /**
     * populateDatabase
     * @param db - the db to populate
     */
    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    /**
     * addCollaborator
     * Add an collaborator
     * @param db - the right database
     * @param name - name of collaborator
     * @param service - service of collaborator
     * @param email - mail of the collaborator
     * @param password - password of the collaborator
     */
    private static void addCollaborator(final AppDatabase db, final String name, final String service,
                                        final String email, final String password) {
        Collaborator collaborator = new Collaborator(name, service, email, password);
        db.collaboratorDao().insert(collaborator);
    }

    /**
     * addAbsences
     * Add an absence
     * @param db - the right database
     * @param startAbsence - the date when the absence begin
     * @param endAbsence - the date when the absence end
     * @param reason - the reason of the absence
     * @param email - the mail of the collaborator who request the absence
     */
    private static void addAbsences(final AppDatabase db, final String startAbsence, final String endAbsence,
                                    final String reason, final String email) {
        Absences absence = new Absences(startAbsence, endAbsence, reason, email);
        db.absencesDao().insert(absence);
    }

    /**
     * populateWithTestData
     * @param db - db to populate
     */
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
                "16.03.2020", "17.03.2020", "sickness", "isAldini@gmail.com"
        );
        addAbsences(db,
                "18.03.2020", "19.03.2020", "sickness", "akHayama@gmail.com"
        );
        addAbsences(db,
                "19.03.2020", "21.03.2020", "vacation", "akHayama@gmail.com"
        );
        addAbsences(db,
                "20.04.2020", "28.04.2020", "vacation", "ryoKuro@gmail.com"
        );
        addAbsences(db,
                "13.03.2020", "17.03.2020", "paternity", "ryoKuro@gmail.com"
        );
        addAbsences(db,
                "28.03.2020", "30.03.2020", "vacation", "saIsshiki@gmail.com"
        );



    }

    /**
     * PopulateDbAsync
     * Populate the db in asynchronous
     */
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
