package com.example.kointest.presentation.views.newnote

import com.example.kointest.domain.Note

interface IAddNewNotepPresenter {

    fun saveNote(note : Note)
    fun updateNote(note : Note)

}