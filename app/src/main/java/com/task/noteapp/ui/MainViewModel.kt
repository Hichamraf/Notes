package com.task.noteapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.NoteRepository
import com.task.noteapp.model.Note
import com.task.noteapp.model.NoteObservable
import com.task.noteapp.utils.Dispatchers
import com.task.noteapp.utils.SingleLiveEvent
import com.task.noteapp.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository, private val validators: Validators,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _notesLiveData = MutableLiveData<List<Note>>()
    val notesLiveData: LiveData<List<Note>> = _notesLiveData

    private val _onNoteSaved = SingleLiveEvent<Boolean>()
    val onNoteSaved: LiveData<Boolean> = _onNoteSaved
    private var selectedNote: Note? = null


    var noteObservable: NoteObservable = NoteObservable(null)

    fun loadNotes() {
        viewModelScope.launch(dispatchers.io) {
            val noteList = noteRepository.getAllNotes()
            _notesLiveData.postValue(noteList)
        }
    }

    fun saveOrUpdateNote() {
        if (isInputFieldsValid()) {
            viewModelScope.launch(dispatchers.io) {
                if (selectedNote == null) {
                    saveNote()

                } else {
                    updateNote()
                }
                _onNoteSaved.postValue(true)
            }
        }
    }

    fun deleteNote() {
        selectedNote?.let {
            viewModelScope.launch(dispatchers.io) {
                noteRepository.delete(it)
                _onNoteSaved.postValue(true)
            }
        }
    }

    private suspend fun updateNote() {
        selectedNote?.title = noteObservable.title
        selectedNote?.description = noteObservable.description
        selectedNote?.imageUrl = noteObservable.imageUrl
        selectedNote?.isEdited = true
        selectedNote?.let {
            noteRepository.update(it)
        }
    }

    private suspend fun saveNote() {
        noteRepository.save(
            Note(
                title = noteObservable.title,
                description = noteObservable.description,
                imageUrl = noteObservable.imageUrl,
                isEdited = false,
                date = getCurrentTime()
            )
        )
    }

    fun setSelectedNote(note: Note?) {
        selectedNote = note
        noteObservable = NoteObservable(note)
    }


    private fun getCurrentTime(): Date {
        return Calendar.getInstance().time
    }

    private fun isUrlEmptyOrValid(imageUrl: String): Boolean {
        return imageUrl.isEmpty() || validators.isUrlValid(imageUrl)
    }

    private fun isInputFieldsValid() =
        noteObservable.title.isNotEmpty() && noteObservable.description.isNotEmpty() && isUrlEmptyOrValid(noteObservable.imageUrl)


}