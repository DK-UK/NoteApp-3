package com.example.noteapp.Listeners;

import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.Note;

public interface CheckListListener {
    void onCheckListClicked(CheckList checkList, int position);

}
