package com.example.kointest.presentation.views.newnote

import com.example.kointest.domain.entity.Note

interface INewNoteContract {

    interface View {

        fun getValuesAndValidate()

        fun confirmExit()

        fun showAlert(msg: String)

        fun confirmDeleteNote()

        fun showAlertEmptyInput()

        fun inputValuesIntent()
    }

    interface Presenter {

        fun insert(note: Note)

        fun updateNote(note: Note)

        fun deleteNote(note: Note)
    }
}
