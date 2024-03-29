package com.example.noteapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.NoteBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NoteBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var notes: List<Note>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.apply {
            val note = notes[position]
            tvTitle.text = note.title
        }

        holder.binding.ibtnEdit.setOnClickListener {
            val intent = Intent(it.context, NoteEditActivity::class.java)
            intent.putExtra("type", false)
            intent.putExtra("id", notes[holder.layoutPosition].id)
            intent.putExtra("title", notes[holder.layoutPosition].title)
            intent.putExtra("description", notes[holder.layoutPosition].description)
            it.context.startActivity(intent)
        }

        holder.binding.ibtnDelete.setOnClickListener{
            val db = NoteDatabase.getDatabase(holder.binding.ibtnEdit.context).noteDao()

            MainScope().launch {
                db.deleteNote(notes[holder.layoutPosition])
                notes = db.getAll()
            }
        }
    }
}