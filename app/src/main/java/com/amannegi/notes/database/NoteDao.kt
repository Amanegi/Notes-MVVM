package com.amannegi.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note WHERE owner = :ownerEmail")
    fun getAllNotes(ownerEmail: String): LiveData<List<Note>>

    @Insert
    suspend fun saveNote(note: Note)
    // added suspend to make use of coroutines

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}