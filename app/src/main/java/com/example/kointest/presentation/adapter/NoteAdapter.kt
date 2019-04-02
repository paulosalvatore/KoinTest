package com.example.kointest.presentation.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kointest.R
import com.example.kointest.domain.entity.Note
import com.example.kointest.presentation.views.newnote.AddNewNoteActivity
import kotlinx.android.synthetic.main.row_note_item.view.*


class NoteAdapter(private val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var mListNote : List<Note> = mutableListOf()

    fun setNote(newList: List<Note>) {
        mListNote = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NoteHolder {
        val view = LayoutInflater.from(context).
            inflate(R.layout.row_note_item, parent, false)
        return NoteHolder(view)
    }



    override fun getItemCount(): Int {
           return mListNote.size

    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val itemNote : Note = mListNote[position]
        holder.title.text = itemNote.titleNote
        holder.body.text = itemNote.contentNote
        holder.date.text = itemNote.dateNote
        setColorViewPriority(itemNote.priorityNote, holder)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddNewNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("ID_NOTE", itemNote)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    private fun setColorViewPriority(priorityNote: Int,holder: NoteHolder) {
        when (priorityNote) {
            ColorPriority.GREEN.getColorPriority() ->
                holder.priority.setBackgroundColor(context.resources.getColor(android.R.color.holo_green_light))

            ColorPriority.YELLOW.getColorPriority() ->
                holder.priority.setBackgroundColor(context.resources.getColor(android.R.color.holo_orange_light))

            else -> holder.priority.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_dark ))
        }
    }


    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.title
        val body = itemView.body
        val date = itemView.date
        val priority : View = itemView.priority

    }



    interface IColorPriority{
        fun getColorPriority() : Int
    }

    enum class ColorPriority : IColorPriority{
        GREEN{
            override fun getColorPriority() = 1
        },
        YELLOW {
            override fun getColorPriority() = 2
        },
        RED {
             override fun getColorPriority() = 3
        }
    }




}