package com.example.noteapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.Dao.CheckListDao;
import com.example.noteapp.Dao.NoteDao;
import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.Note;

@Database(entities = CheckList.class, version = 1, exportSchema = false)
public abstract class CheckListDatabase extends RoomDatabase {

    private static CheckListDatabase checkListDatabase;

    public static synchronized CheckListDatabase getDatabase(Context context){
        if(checkListDatabase == null){
            checkListDatabase = Room.databaseBuilder(
                    context,
                    CheckListDatabase.class,
                    "checklist_db"
            ).build();
        }
        return checkListDatabase;
    }

    public abstract CheckListDao checkListDao();

}
