package com.example.kointest.presentation.views.newnote

import com.example.kointest.domain.entity.Note
import com.example.kointest.infra.repository.NoteRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AddNewNotePresenter(private val view: INewNoteContract.View) : INewNoteContract.Presenter, KoinComponent {


    private val mRepo: NoteRepository by inject()

    override fun insert(note: Note) {
        mRepo.insert(note)
    }

    override fun updateNote(note: Note) {
        mRepo.update(note)
    }

    override fun deleteNote(note: Note) {
        mRepo.deleteNoteId(note)
    }



}

