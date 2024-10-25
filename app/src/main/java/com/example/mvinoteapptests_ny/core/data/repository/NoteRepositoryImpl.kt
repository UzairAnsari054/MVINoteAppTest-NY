package com.example.mvinoteapptests_ny.core.data.repository

import com.example.mvinoteapptests_ny.core.data.local.NoteDatabase
import com.example.mvinoteapptests_ny.core.data.mapper.toNoteEntityForDeletion
import com.example.mvinoteapptests_ny.core.data.mapper.toNoteEntityForInsertion
import com.example.mvinoteapptests_ny.core.data.mapper.toNoteItem
import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.example.mvinoteapptests_ny.core.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    noteDatabase: NoteDatabase
) : NoteRepository {
    private val noteDao = noteDatabase.getNoteDao()

    override suspend fun loadNotes(): List<NoteItem> {
        return noteDao.loadNotes().map { it.toNoteItem() }
    }

    override suspend fun upsertNote(noteItem: NoteItem) {
        return noteDao.upsertNote(noteItem.toNoteEntityForInsertion())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        return noteDao.deleteNote(noteItem.toNoteEntityForDeletion())
    }
}