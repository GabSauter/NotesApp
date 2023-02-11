package com.example.noteapp

import androidx.room.*

@Dao
interface NoteDao {
    @Query ("SELECT * FROM note")
    suspend fun getAll() : List<Note>

    @Query ("SELECT * FROM note WHERE id IN (:noteId)")
    suspend fun getNote(noteId: Int) : Note

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}