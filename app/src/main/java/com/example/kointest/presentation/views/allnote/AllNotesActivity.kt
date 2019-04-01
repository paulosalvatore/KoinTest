package com.example.kointest.presentation.views.allnote

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.kointest.R
import com.example.kointest.domain.Note
import android.arch.lifecycle.ViewModelProviders
import com.example.kointest.domain.NoteViewModel
import com.example.kointest.presentation.adapter.NoteAdapter
import com.example.kointest.presentation.views.newnote.AddNewNoteActivity
import kotlinx.android.synthetic.main.activity_all_notes.*
import org.koin.android.ext.android.inject

class AllNotesActivity : AppCompatActivity(), AllNotesView {


    private val mAdapter : NoteAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_notes)

        val mViewModelProvider = ViewModelProviders.of(this@AllNotesActivity).get(NoteViewModel(application)::class.java)

        configRecycler()

        mViewModelProvider.getAllNotes().observe(this, Observer<List<Note>> {
                mAdapter.setNote(it!!)

        })

        btn_new_note.setOnClickListener {
            startActivity(Intent(this, AddNewNoteActivity::class.java ))
        }

    }

    override fun configRecycler() {
        recycler_all_notes.setHasFixedSize(true)
        recycler_all_notes.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recycler_all_notes.adapter = mAdapter
    }

}
