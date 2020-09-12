package com.example.noteapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.noteapp.Entities.CheckListItem;

import java.util.List;

@Dao
public interface ListItemDao {
    @Query("SELECT * FROM checklistitem ORDER BY id DESC")
    List<CheckListItem> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListItem(CheckListItem checkListItem);

    @Delete
    void deleteListItem(CheckListItem checkListItem);
}
