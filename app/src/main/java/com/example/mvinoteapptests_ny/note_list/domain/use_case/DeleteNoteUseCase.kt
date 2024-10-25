package com.example.mvinoteapptests_ny.note_list.domain.use_case

import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.example.mvinoteapptests_ny.core.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteItem: NoteItem) {
        return noteRepository.deleteNote(noteItem)
    }
}