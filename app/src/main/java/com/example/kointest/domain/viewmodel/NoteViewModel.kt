package com.example.kointest.domain.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.kointest.infra.repository.NoteRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NoteViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val mRepo: NoteRepository by inject()
    private val mListNote = mRepo.getAllNote()

    fun getAllNotes() = mListNote
}
