package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.databinding.NoteEditBinding

class NoteEditActivity : AppCompatActivity() {

    private lateinit var binding: NoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}