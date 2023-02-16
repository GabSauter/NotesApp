package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
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
            intent.putExtra("type", true)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null)
            searchDatabase(newText)
        return true
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"
        MainScope().launch {
            noteAdapter.notes = db.searchDatabase(searchQuery)
        }
    }
}