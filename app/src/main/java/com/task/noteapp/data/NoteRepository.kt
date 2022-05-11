package com.task.noteapp.data

import com.task.noteapp.model.Note

interface NoteRepository {

    suspend fun save(note : Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    suspend fun getAllNotes() : List<Note>
}