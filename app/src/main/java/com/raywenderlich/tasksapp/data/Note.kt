package com.raywenderlich.tasksapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val description : String,
    val date : String,
    var selected : Boolean = false
    ) : Parcelable
