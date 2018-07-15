package com.example.myssd.note;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.myssd.note.modul.Link;

import java.util.List;

@Dao
public interface LinkDao {

    @Query("SELECT * FROM LINK")
    List<Link> getAll();

    @Insert
    void insertAll(Link... links);

    @Update
    void update(Link link);

    @Query("SELECT COUNT(*) from LINK")
    int countLinks();

    @Delete
    void delete(Link link);
}