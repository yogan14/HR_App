package com.example.hr_app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hr_app.entity.Collaborator;

import java.util.List;

@Dao
public interface CollaboratorDao {

    @Insert
    void insert(Collaborator collaborator);

    @Update
    void update(Collaborator collaborator);

    @Delete
    void delete(Collaborator collaborator);

    @Query("SELECT * FROM collaborator_table ORDER BY name")
    LiveData<List<Collaborator>> getAllCollaborators();

}
