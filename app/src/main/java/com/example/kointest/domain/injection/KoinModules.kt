package com.example.kointest.domain.injection

import android.arch.persistence.room.Room
import com.example.kointest.infra.NoteRepository
import com.example.kointest.infra.AppDatabase
import com.example.kointest.presentation.adapter.NoteAdapter
import org.koin.dsl.module.module


val dbModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java,"note_database").build()}

    single { get<AppDatabase>().noteDao() }

    factory { NoteRepository() }

}

val adapterModule = module {
    factory { NoteAdapter(context = get()) }

}
