package com.example.mvinoteapptests_ny.note_list.domain.use_case

import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.example.mvinoteapptests_ny.core.domain.repository.NoteRepository
import javax.inject.Inject

class LoadNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(isOrderByTitle: Boolean): List<NoteItem> {
        return if (isOrderByTitle) {
            noteRepository.loadNotes().sortedBy { it.title.lowercase() }
        } else {
            noteRepository.loadNotes().sortedBy { it.dateAdded }
        }

    }
}