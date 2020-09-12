package com.example.noteapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.CheckListItem;

import java.util.List;

@Dao
public interface CheckListDao {

    @Query("SELECT * FROM checklist ORDER BY id DESC")
    List<CheckListItem> getAllLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(CheckList checkList);

    @Delete
    void deleteList(CheckList checkList);
}
