package com.task.noteapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteItemBinding
import com.task.noteapp.model.Note

class NoteAdapter constructor(private val noteList: List<Note>,private val requestManager: RequestManager,private val onNoteClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = DataBindingUtil.inflate<NoteItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.note_item, parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.note = noteList[position]
        noteList[position].imageUrl?.let {
            if (it.isNotEmpty()){
                requestManager.load(it).into(holder.binding.noteImg)
            }
        }
        holder.binding.root.setOnClickListener {
            onNoteClick.invoke(noteList[position])
        }
    }


    override fun getItemCount(): Int {
       return  noteList.size
    }


    class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)
}