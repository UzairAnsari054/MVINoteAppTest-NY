package com.example.mvinoteapptests_ny.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteapptests_ny.core.data.repositoryrepository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoadNotesUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var loadNotesUseCase: LoadNotesUseCase

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        loadNotesUseCase = LoadNotesUseCase(fakeNoteRepository)
    }

    @Test
    fun `get notes from empty list, list is empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)
        val notes = loadNotesUseCase.invoke(true)
        assertThat(notes.isEmpty()).isTrue()
    }

    @Test
    fun `get notes from filled list, list is not empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = loadNotesUseCase.invoke(true)
        assertThat(notes.isNotEmpty()).isTrue()
    }

    @Test
    fun `get notes sorted by title, note list sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = loadNotesUseCase.invoke(true)

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes sorted by date, note list sorted by date`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = loadNotesUseCase.invoke(false)

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }
}