package com.task.noteapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.task.noteapp.data.local.AppDatabase
import com.task.noteapp.data.local.NoteDao
import com.task.noteapp.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class NoteDaoTest {

    private lateinit var subject: NoteDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        subject = db.noteDao()
    }

    @Test
    fun test_save_persist_user_into_db() {
        runBlocking {
            val note = noteList.first()
            subject.save(note)
            val noteFromDb = subject.getAllNotes()
            assertEquals(listOf(note), noteFromDb)
        }
    }

    @Test
    fun test_update_updates_existing_note() {
        runBlocking {
            val note = noteList.first()
            subject.save(note)
            val newTitle = "new title"
            val newDesc = "new desc"
            val newUrl = "newURl"
            note.isEdited = true
            note.title = newTitle
            note.description = newDesc
            note.imageUrl = newUrl
            subject.update(note)
            val noteFromDb = subject.getAllNotes()
            assertEquals(listOf(note), noteFromDb)
        }
    }

    @Test
    fun test_delete_note_removes_note_from_db() {
        runBlocking {
            noteList.forEach {
                subject.save(it)
            }
            subject.delete(noteList[1])
            val notesFromDb = subject.getAllNotes()
            assertEquals(2, notesFromDb.size)
            assertFalse(notesFromDb.contains(noteList[1]))
            assertTrue(notesFromDb.contains(noteList.first()))
            assertTrue(notesFromDb.contains(noteList.last()))
        }
    }

    @Test
    fun test_getAll_returns_all_save_notes() {
        runBlocking {
            noteList.forEach {
                subject.save(it)
            }
            val notesFromDb = subject.getAllNotes()
            assertEquals(3, notesFromDb.size)
        }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        db.close()
    }

    private val noteList = listOf(
        Note(id = 1, title = "title", description = "description", date = Calendar.getInstance().time, imageUrl = ""),
        Note(id = 2, title = "title2", description = "description2", date = Calendar.getInstance().time, imageUrl = ""),
        Note(id = 3, title = "title3", description = "description3", date = Calendar.getInstance().time, imageUrl = "")
    )
}