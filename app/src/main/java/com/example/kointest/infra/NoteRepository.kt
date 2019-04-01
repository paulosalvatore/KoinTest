package com.example.kointest.infra

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.kointest.domain.Note
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NoteRepository: KoinComponent, IRepositoryView {


    private var mListAllNotes : LiveData<List<Note>> = MutableLiveData<List<Note>>()
    private val mDao : NoteDao by inject()
    private val LOG_TAG = NoteRepository::class.java.simpleName
    lateinit var compositeDisposable: CompositeDisposable

    init {
        getAll()
    }

    @SuppressLint("CheckResult")
    override fun getAll() {
        Single.create<LiveData<List<Note>>> {
            emitter ->
            emitter.onSuccess(mDao.getAllNotes())
        }.retry (2)
            .subscribe (
                {
                    mListAllNotes = it
                },{
                    Log.e(LOG_TAG, "Error: ${it.message}")
                }
            ).dispose()
    }

    override fun getAllNote() : LiveData<List<Note>>{
        return mListAllNotes
    }

    @SuppressLint("CheckResult")
    override fun insert(note: Note){

       Completable.fromAction { mDao.insert(note) }
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe (
               {
                   Log.i(LOG_TAG, "Note ${note.titleNote} save success")
               }, {
                   Log.e(LOG_TAG, "Error to save the data: " + it.message)
               }
           )
    }

    @SuppressLint("CheckResult")
    override fun update(note: Note) {
        Completable.fromAction { mDao.update(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LOG_TAG, "Update note ${note.titleNote}")
            },{
                Log.e(LOG_TAG, "Error ${it.message} " )
            })
    }


    @SuppressLint("CheckResult")
    override fun deleteNoteId(note: Note) {
        Completable.fromAction { mDao.deleteNote(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LOG_TAG, "Delete note ${note.titleNote}")
            },{
                Log.e(LOG_TAG, "Error ${it.message} " )
            })
    }

}