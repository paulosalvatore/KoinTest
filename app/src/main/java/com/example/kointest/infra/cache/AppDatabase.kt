package com.example.kointest.infra.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kointest.domain.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase (){
    abstract fun noteDao() : NoteDao
}