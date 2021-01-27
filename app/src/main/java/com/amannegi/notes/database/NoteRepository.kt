package com.amannegi.notes.database

import android.app.Application
import androidx.lifecycle.LiveData

// this class handles all the incoming data either from local db or remote db
class NoteRepository(application: Application) {

    private val noteDao = NoteDatabase.getDatabaseInstance(application)!!.noteDao()

    fun getAllNotes(email: String): LiveData<List<Note>> {
        return noteDao.getAllNotes(email)
    }

    suspend fun saveNote(note: Note) {
        noteDao.saveNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}