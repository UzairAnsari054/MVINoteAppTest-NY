package com.example.mvinoteapptests_ny.core.domain.model

data class NoteItem(
    val title: String,
    val description: String,
    val imageUrl: String,
    val dateAdded: Long,
    val id: Int? = null
)
