package com.example.kointest.infra

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kointest.domain.Note
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

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