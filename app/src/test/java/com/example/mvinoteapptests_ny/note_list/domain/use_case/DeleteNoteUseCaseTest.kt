package com.example.mvinoteapptests_ny.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteapptests_ny.core.data.repositoryrepository.FakeNoteRepository
import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNoteUseCaseTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Before
    fun setUp(){
        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `delete note from list, note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val note = NoteItem("title2", "description2", "imageUrl2", 2)
        deleteNoteUseCase.invoke(note)
        assertThat(!fakeNoteRepository.loadNotes().contains(note)).isTrue()
    }
}