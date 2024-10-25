package com.example.mvinoteapptests_ny.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteapptests_ny.core.data.repositoryrepository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNoteUseCase: UpsertNoteUseCase

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNoteUseCase = UpsertNoteUseCase(fakeNoteRepository)
    }


    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val isInserted = upsertNoteUseCase("", "description", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val isInserted = upsertNoteUseCase("title", "", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert valid note, return true`() = runTest {
        val isInserted = upsertNoteUseCase("title", "description", "image")
        assertThat(isInserted).isTrue()
    }
}