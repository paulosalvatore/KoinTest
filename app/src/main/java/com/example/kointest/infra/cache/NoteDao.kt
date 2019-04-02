package com.example.kointest.infra.cache

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kointest.domain.entity.Note
import io.reactivex.Maybe

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note : Note)

    @Query("SELECT * FROM note_table")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE id =  :id")
    fun getNoteById(id : Int) : Maybe<Note>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun update(vararg note: Note)
}