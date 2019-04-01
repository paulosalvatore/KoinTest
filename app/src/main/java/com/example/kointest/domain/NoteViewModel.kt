package com.example.kointest.domain

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kointest.infra.NoteRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NoteViewModel constructor(application: Application) : AndroidViewModel(application), KoinComponent{

    private var mListNote : LiveData<List<Note>> = MutableLiveData<List<Note>>()
    private val mRepo : NoteRepository by inject()

    init {
        mListNote.let {
            mListNote = mRepo.getAllNote()
        }
    }

    fun getAllNotes() = mListNote

    fun insert(note: Note) = mRepo.insert(note)

    fun updateNote(note : Note) = mRepo.update(note)

    fun deleteNote(note: Note) =  mRepo.deleteNoteId(note)

}
