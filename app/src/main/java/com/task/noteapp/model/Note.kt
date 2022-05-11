package com.task.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "notes")
data class Note constructor(
    @PrimaryKey(autoGenerate = true) val id : Int?=null,
    var title: String,
    var description: String,
    var imageUrl: String?,
    val date: Date,
    var isEdited: Boolean = false)