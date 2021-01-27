package com.amannegi.notes.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.amannegi.notes.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(app: Application) : AndroidViewModel(app) {

    private val repository : NoteRepository = NoteRepository(app)
    val notes: LiveData<List<Note>>

    init {
        val userEmail = SessionManager(app).getEmail()
        notes = repository.getAllNotes(userEmail!!)
    }

    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.saveNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteNote(note)
    }
}