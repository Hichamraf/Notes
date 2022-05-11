package com.task.noteapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.data.NoteRepository
import com.task.noteapp.model.Note
import com.task.noteapp.model.NoteObservable
import com.task.noteapp.utils.TestDispatchers
import com.task.noteapp.utils.Validators
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class MainViewModelTest {

    private val noteRepo = mockk<NoteRepository>(relaxUnitFun = true)
    private val validators = spyk<Validators>()
    private val dispatchers = TestDispatchers
    private lateinit var subject: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        subject = MainViewModel(noteRepo, validators, dispatchers)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test on loadNotes is called get all from repo is called and set to notesLivedata`() {
        runBlockingTest {
            coEvery { noteRepo.getAllNotes() } returns noteList
            subject.loadNotes()
            coVerify(exactly = 1) { noteRepo.getAllNotes() }
            assertEquals(noteList, subject.notesLiveData.value)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test saveOrUpdateNote calls save when selectedNote is null`() {
        runBlockingTest {
            subject.noteObservable = NoteObservable(noteList.first())
            subject.saveOrUpdateNote()
            coVerify(exactly = 1) {
                noteRepo.save(withArg {
                    assertEquals(noteList.first().title, it.title)
                    assertEquals(noteList.first().description, it.description)
                    assertFalse(it.isEdited)
                })
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test saveOrUpdateNote calls update when selectedNote is not null and set isEdited to true`() {
        runBlockingTest {
            val newTitle = "new Title"
            subject.setSelectedNote(noteList.first())
            subject.noteObservable.title = newTitle
            subject.saveOrUpdateNote()
            coVerify(exactly = 1) {
                noteRepo.update(withArg {
                    assertEquals(newTitle, it.title)
                    assertEquals(noteList.first().description, it.description)
                    assertTrue(it.isEdited)
                })
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test saveOrUpdateNote des not call save or update when input fields are invalid`() {
        every { validators.isUrlValid(any()) } returns false
        runBlockingTest {
            subject.saveOrUpdateNote()
            coVerify(exactly = 0) { noteRepo.save(any()) }
            coVerify(exactly = 0) { noteRepo.update(any()) }
            subject.noteObservable.title = "title"
            subject.saveOrUpdateNote()
            coVerify(exactly = 0) { noteRepo.save(any()) }
            coVerify(exactly = 0) { noteRepo.update(any()) }
            subject.noteObservable.title = ""
            subject.noteObservable.description = "desc"
            subject.saveOrUpdateNote()
            coVerify(exactly = 0) { noteRepo.save(any()) }
            coVerify(exactly = 0) { noteRepo.update(any()) }

            subject.noteObservable.title = "title"
            subject.noteObservable.description = "desc"
            subject.noteObservable.imageUrl = "invalid url"
            subject.saveOrUpdateNote()
            coVerify(exactly = 0) { noteRepo.save(any()) }
            coVerify(exactly = 0) { noteRepo.update(any()) }

        }
    }

    @Test
    fun `test saveOrUpdateNote image url is optional`(){
        subject.noteObservable.title = "title"
        subject.noteObservable.description = "desc"
        subject.saveOrUpdateNote()
        coVerify(exactly = 1) { noteRepo.save(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test deleteNote calls delete note from repo`() {
        runBlockingTest {
            subject.setSelectedNote(noteList.first())
            subject.deleteNote()
            coVerify(exactly = 1) {
                noteRepo.delete(withArg {
                    assertEquals(noteList.first().title, it.title)
                    assertEquals(noteList.first().description, it.description)
                })
            }
        }
    }

    @Test
    fun `test setSelectedNote sets noteObservable`() {
        assertNull(subject.noteObservable.note)
        subject.setSelectedNote(noteList.first())
        assertEquals(noteList.first(), subject.noteObservable.note)
    }

    private val noteList = listOf(
        Note(id = 1, title = "title", description = "description", date = Calendar.getInstance().time, imageUrl = ""),
        Note(id = 2, title = "title2", description = "description2", date = Calendar.getInstance().time, imageUrl = ""),
        Note(id = 3, title = "title3", description = "description3", date = Calendar.getInstance().time, imageUrl = "")
    )

    @After
    fun tearDown() = unmockkAll()
}