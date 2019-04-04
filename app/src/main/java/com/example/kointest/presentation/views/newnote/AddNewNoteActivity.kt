package com.example.kointest.presentation.views.newnote

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.kointest.R
import com.example.kointest.domain.entity.Note
import com.example.kointest.domain.utils.Priority
import kotlinx.android.synthetic.main.activity_add_new_note.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class AddNewNoteActivity : AppCompatActivity(), INewNoteContract.View {

    private val presenter by inject<INewNoteContract.Presenter> { parametersOf(this) }

    private var priority = Priority.LOW

    val note: Note? by lazy {
        intent?.extras?.let {
            it.getSerializable("ID_NOTE") as Note
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        inputValuesIntent()

        btn_confirm_note.setOnClickListener {
            getValuesAndValidate()
        }
    }

    override fun inputValuesIntent() {
        note?.let {
            input_title.setText(it.titleNote)
            input_body.setText(it.contentNote)

            last_update.visibility = View.VISIBLE
            last_update.append(" ${it.dateNote}")

            when (it.priorityNote) {
                Priority.LOW.ordinal -> radio_group_priority.check(prio_1.id)
                Priority.MEDIUM.ordinal -> radio_group_priority.check(prio_2.id)
                Priority.HIGH.ordinal -> radio_group_priority.check(prio_3.id)
            }
        }
    }


    override fun getValuesAndValidate() {
        val title = Editable.Factory.getInstance().newEditable(input_title.text).toString()
        val body = Editable.Factory.getInstance().newEditable(input_body.text).toString()
        val sdf = SimpleDateFormat("dd/MM hh:mm", Locale.getDefault())
        val dateFormat = sdf.format(Date())

        val id = radio_group_priority.checkedRadioButtonId

        when (id) {
            prio_1.id -> {
                priority = Priority.LOW
            }
            prio_2.id -> {
                priority = Priority.MEDIUM
            }
            prio_3.id -> {
                priority = Priority.HIGH
            }
        }

        if (title.isEmpty() || body.isEmpty() || id == -1) {
            showAlertEmptyInput()
        } else {
            note?.let {
                val updateNote = Note(
                    it.id,
                    titleNote = title,
                    contentNote = body,
                    dateNote = dateFormat,
                    priorityNote = priority.ordinal
                )

                presenter.updateNote(updateNote)
            } ?: run {
                val newNote = Note(
                    titleNote = title,
                    contentNote = body,
                    dateNote = dateFormat,
                    priorityNote = priority.ordinal
                )

                presenter.insert(newNote)
            }

            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> confirmExit()
            R.id.action_delete -> {
                confirmDeleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun confirmDeleteNote() {
        note?.let {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.label_dialog_title))
                .setMessage(getString(R.string.confirm_delete_message, it.titleNote))
                .setPositiveButton(getString(R.string.label_delete_note)) { _, _ ->
                    presenter.deleteNote(it)
                    finish()
                }.setNegativeButton(getString(R.string.btn_dialog_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }.setCancelable(false).create().show()
        }
    }

    override fun confirmExit() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.label_dialog_title))
            .setMessage(getString(R.string.confirm_exit_message))
            .setPositiveButton(getString(R.string.btn_exit_dialog)) { _, _ ->
                finish()
            }.setNegativeButton(getString(R.string.btn_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false).create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun showAlert(msg: String) {
        Snackbar.make(constraint_new_note, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun showAlertEmptyInput() {
        showAlert(getString(R.string.error_input_empty))
    }
}
