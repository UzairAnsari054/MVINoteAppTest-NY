package com.example.mvinoteapptests_ny.core.domain.repository

import com.example.mvinoteapptests_ny.core.domain.model.NoteItem

interface NoteRepository {
    suspend fun loadNotes(): List<NoteItem>
    suspend fun upsertNote(noteItem: NoteItem)
    suspend fun deleteNote(noteItem: NoteItem)
}