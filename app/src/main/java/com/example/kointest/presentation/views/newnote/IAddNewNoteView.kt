package com.example.kointest.presentation.views.newnote

interface IAddNewNoteView {

    fun getValuesAndValidate()
    fun confirmExit()
    fun showAlert(msg : String)
    fun confirmDeleteNote()
}