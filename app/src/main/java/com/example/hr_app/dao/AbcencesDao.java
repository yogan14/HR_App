package com.example.hr_app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hr_app.entity.Absences;
import java.util.List;

@Dao
public interface AbcencesDao {

    @Insert
    void insert(Absences absences);

    @Update
    void update(Absences absences);

    @Delete
    void delete(Absences absences);

    @Query("SELECT * FROM absences_table ORDER BY startAbsence")
    LiveData<List<Absences>> getAllAbsences();

}
