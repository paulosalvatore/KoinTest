package com.example.kointest.domain.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kointest.domain.entity.Note
import com.example.kointest.infra.repository.NoteRepository
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

}
