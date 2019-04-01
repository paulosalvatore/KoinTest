package com.example.kointest.presentation.views.newnote.di

import com.example.kointest.presentation.views.newnote.AddNewNotePresenter
import com.example.kointest.presentation.views.newnote.INewNoteContract
import org.koin.dsl.module.module

object ModuleUi {

    val moduleUi = module {

        factory<INewNoteContract.Presenter> { (view: INewNoteContract.View) ->
            AddNewNotePresenter(view = view)
        }
    }

}