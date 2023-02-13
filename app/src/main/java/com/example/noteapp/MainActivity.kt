package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var noteAdapter: NoteAdapter
    private lateinit var db: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        db = NoteDatabase.getDatabase(this).noteDao()

        lifecycleScope.launchWhenCreated {
            noteAdapter.notes = db.getAll()
        }

        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }


    }

    private fun setupRecyclerView() = binding.rvNotes.apply {
        noteAdapter = NoteAdapter()
        adapter = noteAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            noteAdapter.notes = db.getAll()
        }
    }
}