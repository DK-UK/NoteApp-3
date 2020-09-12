package com.example.noteapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.Dao.CheckListDao;
import com.example.noteapp.Entities.CheckListItem;

@Database(entities = CheckListItem.class, version = 1, exportSchema = false)
public abstract class ListItemDatabase extends RoomDatabase {

    private static ListItemDatabase listItemDatabase;

    public static synchronized ListItemDatabase getDatabase(Context context){
        if(listItemDatabase == null){
            listItemDatabase = Room.databaseBuilder(
                    context,
                    ListItemDatabase.class,
                    "listitem_db"
            ).build();
        }
        return listItemDatabase;
    }

    public abstract CheckListDao ListItemDao();

}
