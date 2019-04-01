package com.example.kointest.infra

import android.arch.lifecycle.LiveData
import com.example.kointest.domain.Note

interface IRepositoryView {

    fun getAll()
    fun getAllNote() : LiveData<List<Note>>
    fun insert(note: Note)
    fun deleteNoteId(note: Note)
    fun update(note: Note)
}