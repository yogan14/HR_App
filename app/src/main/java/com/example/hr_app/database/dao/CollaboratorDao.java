package com.example.hr_app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.example.hr_app.database.entity.Collaborator;

import java.util.List;


/**
 * CollaboratorDao
 * Queries to the Collaborator table
 */
@Dao
public interface CollaboratorDao {

    @Query("SELECT * FROM Collaborator WHERE email = :id")
    LiveData<Collaborator> getOneCollaborator(String id);

    @Query("SELECT * FROM Collaborator")
    LiveData<List<Collaborator>> getAll();

    @Insert
    void insert(Collaborator collaborator) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCollaborators(List<Collaborator> collaborators);

    @Update
    void update(Collaborator collaborator);

    @Delete
    void delete(Collaborator collaborator);

    @Query("DELETE FROM Collaborator")
    void deleteAll();
}
