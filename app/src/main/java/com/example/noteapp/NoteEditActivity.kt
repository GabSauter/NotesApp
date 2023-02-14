package com.example.noteapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.noteapp.databinding.NoteEditBinding

class NoteEditActivity : AppCompatActivity() {

    private lateinit var binding: NoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val typeCreate: Boolean = intent.getBooleanExtra("type", false)
        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")

        val db = NoteDatabase.getDatabase(this).noteDao()

        if (typeCreate){ // Type 1 = create note
            binding.btnEdit.text = "Create Note"

            binding.btnEdit.setOnClickListener {
                lifecycleScope.launchWhenCreated {

                    if (binding.etTitleEdit.text != null && binding.etDescriptionEdit.text != null) {
                        db.insertNote(
                            Note(
                                0,
                                binding.etTitleEdit.text.toString(),
                                binding.etDescriptionEdit.text.toString()
                            )
                        )

                    } else {
                        Toast.makeText(
                            this@NoteEditActivity,
                            "Please enter the fields some text",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            }

        }else{ // Type != 1 = edit note
            binding.btnEdit.text = "Edit Note"
            binding.etTitleEdit.setText(title)
            binding.etDescriptionEdit.setText(description)
            binding.btnEdit.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    if (binding.etTitleEdit.text != null && binding.etDescriptionEdit.text != null) {
                        db.updateNote(
                            Note(
                                id,
                                binding.etTitleEdit.text.toString(),
                                binding.etDescriptionEdit.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(
                            this@NoteEditActivity,
                            "Please enter the fields some text",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            }
        }
    }
}