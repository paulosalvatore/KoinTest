package com.example.kointest.infra.repository

import android.util.Log
import com.example.kointest.domain.entity.ErrorHandlings
import com.example.kointest.domain.entity.Note
import com.example.kointest.infra.cache.NoteDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NoteRepository : KoinComponent, IRepositoryView {

    companion object {
        private const val LOG_TAG = "NoteRepository"
    }

    private val mDao: NoteDao by inject()

    override fun getAllNote() = mDao.getAllNotes()

    override fun insert(note: Note) {
        val disposable = Completable.fromAction { mDao.insert(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i(LOG_TAG, "Saved note ${note.titleNote} successfully.")
                }, {
                    throw ErrorHandlings(it)
                }
            )
    }

    override fun update(note: Note) {
        val disposable = Completable.fromAction { mDao.update(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LOG_TAG, "Updated note ${note.titleNote} successfully.")
            }, {
                throw ErrorHandlings(it)
            })
    }

    override fun deleteNoteId(note: Note) {
        val disposable = Completable.fromAction { mDao.deleteNote(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LOG_TAG, "Deleted note ${note.titleNote} successfully.")
            }, {
                throw ErrorHandlings(it)
            })
    }
}
