package com.example.kointest.presentation.application

import android.app.Application
import com.example.kointest.domain.di.adapterModule
import com.example.kointest.domain.di.dbModule
import com.example.kointest.presentation.views.newnote.di.ModuleUi.moduleUi
import org.koin.android.ext.android.startKoin

class NotesApplication : Application(){

    override fun onCreate() {
        super.onCreate()

       startKoin(this, listOf(dbModule, adapterModule, moduleUi))
    }
}