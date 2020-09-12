package com.example.noteapp.Listeners;

import com.example.noteapp.Entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
