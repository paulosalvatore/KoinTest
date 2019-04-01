package com.example.kointest.presentation.views.newnote

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.kointest.R
import com.example.kointest.domain.Note
import com.example.kointest.domain.NoteViewModel
import com.example.kointest.domain.utils.Enums
import kotlinx.android.synthetic.main.activity_add_new_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNewNoteActivity : AppCompatActivity(), IAddNewNoteView {



    private var priority : Int = 0
    lateinit var mViewModel : NoteViewModel
    lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true )

        mViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        intent?.extras?.let {
                note = intent!!.extras!!.getSerializable("ID_NOTE") as Note
                input_title.setText(note.titleNote)
                input_body.setText(note.contentNote)
                last_update.visibility = View.VISIBLE
                last_update.append(" " + note.dateNote)
                when(note.priorityNote){
                    Enums.Companion.Priority.LOW.getPriority() -> radio_group_priority.check(prio_1.id)
                    Enums.Companion.Priority.MEDIUM.getPriority()  -> radio_group_priority.check(prio_2.id)
                    Enums.Companion.Priority.HIGH.getPriority() -> radio_group_priority.check(prio_3.id)
                }
        }

        btn_confirm_note.setOnClickListener {
            getValuesAndValidate()
        }
    }


    override fun getValuesAndValidate() {

        val title  = Editable.Factory.getInstance().newEditable(input_title.text).toString()
        val body = Editable.Factory.getInstance().newEditable(input_body.text).toString()
        val sdf = SimpleDateFormat("dd/MM hh:mm", Locale.getDefault())
        val dateFormat = sdf.format(Date())

        val id = radio_group_priority.checkedRadioButtonId
        when(id){
            prio_1.id -> {
                priority = Enums.Companion.Priority.LOW.getPriority()

            }
            prio_2.id -> {
                priority = Enums.Companion.Priority.MEDIUM.getPriority()
            }
            prio_3.id -> {
                priority = Enums.Companion.Priority.HIGH.getPriority()
            }
        }

        if(title.isEmpty() || body.isEmpty() || id.equals(-1)){
            showAlert(getString(R.string.error_input_empty))
        } else {
            if(::note.isInitialized){
                    note = Note(
                        note.id ,
                        titleNote = title,
                        contentNote =  body,
                        dateNote =  dateFormat,
                        priorityNote = priority)
                    mViewModel.updateNote(note)
                    showAlert(getString(R.string.label_update_note))
                    finish()
            }else{
                note = Note(titleNote = title,
                    contentNote = body,
                    dateNote = dateFormat,
                    priorityNote = priority)

                mViewModel.insert(note)
                showAlert("Nota ${note.titleNote} salva!")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> confirmExit()
            R.id.action_delete -> {
               confirmDeleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun confirmDeleteNote() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.label_dialog_title))
            .setMessage("Tem certeza que deseja excluir a nota ${note.titleNote} ?")
            .setPositiveButton(getString(R.string.label_delete_note)){ _, _ ->
                mViewModel.deleteNote(note)
                finish()
            }.setNegativeButton(getString(R.string.btn_dialog_cancel)){ dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false).create().show()

}

    override fun confirmExit() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.label_dialog_title))
            .setMessage("Deseja realmente sair?\nCaso haja alterações, não serão salvas.")
            .setPositiveButton(getString(R.string.btn_exit_dialog)){ _, _ ->
                mViewModel.deleteNote(note)
                finish()
            }.setNegativeButton(getString(R.string.btn_dialog_cancel)){ dialog, _ ->
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


}
