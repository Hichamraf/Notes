package com.task.noteapp.data.local

import com.task.noteapp.data.NoteRepository
import com.task.noteapp.model.Note
import javax.inject.Inject

 class NoteRepoLocalImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun save(note: Note) {
        noteDao.save(note)
    }

    override suspend fun update(note: Note) {
        noteDao.update(note)
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }
}