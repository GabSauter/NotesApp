package com.example.noteapp

import androidx.room.*

@Dao
interface NoteDao {
    @Query ("SELECT * FROM note")
    fun getAll() : List<Note>

    @Query ("SELECT * FROM note WHERE id IN (:noteId)")
    fun getNote(noteId: Int) : Note

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)
}