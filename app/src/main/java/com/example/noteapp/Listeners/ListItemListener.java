package com.example.noteapp.Listeners;

import com.example.noteapp.Entities.CheckListItem;

public interface ListItemListener {
    void onListItemClicked(CheckListItem checkListItem, int position);
}
