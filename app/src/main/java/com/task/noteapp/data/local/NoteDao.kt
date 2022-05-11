package com.task.noteapp.data.local

import androidx.room.*
import com.task.noteapp.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun save(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY date ASC")
    suspend fun getAllNotes(): List<Note>
}