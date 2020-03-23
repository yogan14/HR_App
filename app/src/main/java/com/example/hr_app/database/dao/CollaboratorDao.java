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
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface CollaboratorDao {

    @Query("SELECT * FROM Collaborator WHERE email = :id")
    LiveData<Collaborator> getOneCollaborator(String id);

    @Query("SELECT * FROM collaborator")
    LiveData<List<Collaborator>> getAll();

    @Query("SELECT * FROM collaborator")
    List<Collaborator> getTest();
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
