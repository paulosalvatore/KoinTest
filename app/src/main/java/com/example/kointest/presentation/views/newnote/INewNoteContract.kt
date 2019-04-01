package com.example.kointest.presentation.views.newnote

import com.example.kointest.domain.Note

interface INewNoteContract {

    interface View{

        fun getValuesAndValidate()
        fun confirmExit()
        fun showAlert(msg : String)
        fun confirmDeleteNote()
    }

    interface Presenter{

        fun insert(note: Note)
        fun updateNote(note : Note)
        fun deleteNote(note: Note)
    }
}