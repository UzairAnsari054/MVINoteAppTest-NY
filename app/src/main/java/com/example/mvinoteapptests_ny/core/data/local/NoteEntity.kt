package com.example.mvinoteapptests_ny.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val dateAdded: Long = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)