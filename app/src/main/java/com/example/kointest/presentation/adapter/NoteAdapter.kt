package com.example.kointest.presentation.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kointest.R
import com.example.kointest.domain.entity.Note
import kotlinx.android.synthetic.main.row_note_item.view.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    var mListNote = listOf<Note>()
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    var listener: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_note_item, parent, false)
        return NoteHolder(view)
    }

    override fun getItemCount() = mListNote.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item: Note = mListNote[position]
        holder.bindView(item, listener)
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val priority: View = itemView.priority

        val context = itemView.context

        fun bindView(item: Note, listener: ((Note) -> Unit)?) = with(itemView) {
            title.text = item.titleNote
            body.text = item.contentNote
            date.text = item.dateNote

            setColorViewPriority(item.priorityNote)

            listener?.let {
                setOnClickListener { it(item) }
            }
        }

        private fun setColorViewPriority(priorityNote: Int) {
            when (priorityNote) {
                ColorPriority.GREEN.getColorPriority() ->
                    priority.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))

                ColorPriority.YELLOW.getColorPriority() ->
                    priority.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))

                else -> priority.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            }
        }

        // TODO("Extrair interface e enum")
        interface IColorPriority {
            fun getColorPriority(): Int
        }

        enum class ColorPriority : IColorPriority {
            GREEN {
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
}
