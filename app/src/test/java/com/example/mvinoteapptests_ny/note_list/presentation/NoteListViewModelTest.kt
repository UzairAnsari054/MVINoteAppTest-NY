package com.example.mvinoteapptests_ny.note_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteapptests_ny.MainCoroutineRule
import com.example.mvinoteapptests_ny.core.data.repositoryrepository.FakeNoteRepository
import com.example.mvinoteapptests_ny.note_list.domain.use_case.DeleteNoteUseCase
import com.example.mvinoteapptests_ny.note_list.domain.use_case.LoadNotesUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var loadNotesUseCase: LoadNotesUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var noteListViewModel: NoteListViewModel

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        loadNotesUseCase = LoadNotesUseCase(fakeNoteRepository)
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)
        noteListViewModel = NoteListViewModel(loadNotesUseCase, deleteNoteUseCase)
    }

    @Test
    fun `get notes from empty list, list is empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(noteListViewModel.noteListState.value.isEmpty()).isTrue()
    }

    @Test
    fun `get notes from filled list, list is not empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(noteListViewModel.noteListState.value.isNotEmpty()).isTrue()
    }

    @Test
    fun `delete note from list, note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        fakeNoteRepository.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val note = fakeNoteRepository.loadNotes()[2]

        noteListViewModel.deleteNote(note)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(!noteListViewModel.noteListState.value.contains(note)).isTrue()
    }

    @Test
    fun `sort notes by title, sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        fakeNoteRepository.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val notes = noteListViewModel.noteListState.value
        for (i in 1..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }


    @Test
    fun `sort notes by date, sorted by date`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        fakeNoteRepository.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        var notes = noteListViewModel.noteListState.value
        for (i in 1..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        notes = noteListViewModel.noteListState.value
        for (i in 1..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        notes = noteListViewModel.noteListState.value
        for (i in 1..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }
}