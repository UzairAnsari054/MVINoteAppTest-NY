package com.example.mvinoteapptests_ny.core.data.repositoryrepository

import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.example.mvinoteapptests_ny.core.domain.repository.NoteRepository

class FakeNoteRepository : NoteRepository {
    private var noteItems = mutableListOf<NoteItem>()

    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        noteItems = if (shouldHaveFilledList) {
            mutableListOf(
                NoteItem("title1", "description1", "imageUrl1", 1),
                NoteItem("title2", "description2", "imageUrl2", 2),
                NoteItem("title3", "description3", "imageUrl3", 3),
                NoteItem("title4", "description4", "imageUrl4", 4),
            )
        } else {
            mutableListOf()
        }
    }

    override suspend fun loadNotes(): List<NoteItem> {
        return noteItems
    }

    override suspend fun upsertNote(noteItem: NoteItem) {
        noteItems.add(noteItem)
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteItems.remove(noteItem)
    }

}