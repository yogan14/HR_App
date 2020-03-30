package com.example.hr_app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hr_app.database.entity.Absences;

import java.util.List;

/**
 * AbsenceDao
 * Queries to the Absence table
 */
@Dao
public interface AbsencesDao {


    @Query("SELECT * FROM Absences WHERE validate != :vrai")
    LiveData<List<Absences>> getAbsencesNotValidate(boolean vrai);

    @Query("SELECT * FROM Absences WHERE email = :idCollaborator")
    LiveData<List<Absences>> getAbsencesForOneCollaborator(String idCollaborator);

    @Query("SELECT * FROM Absences WHERE idAbsence = :absencesID")
    LiveData<Absences> getAbsenceByID(int absencesID);

    @Insert
    void insert(Absences absences);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Absences> accounts);

    @Update
    void update(Absences absences);

    @Delete
    void delete(Absences absences);

    @Query("DELETE FROM Absences")
    void deleteAll();

}
