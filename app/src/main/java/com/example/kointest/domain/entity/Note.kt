package com.example.kointest.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titleNote: String,
    val contentNote: String,
    val priorityNote: Int,
    val dateNote: String
) : Serializable
