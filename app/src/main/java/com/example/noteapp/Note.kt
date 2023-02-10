package com.example.noteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String
)
