package com.example.mvinoteapptests_ny.core.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.mvinoteapptests_ny.core.di.AppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        hiltRule.inject()
        noteDao = noteDatabase.getNoteDao()
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun upsertNote_NoteEntityIsUpserted() = runTest {
        val note = NoteEntity(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            dateAdded = System.currentTimeMillis(),
            id = 1
        )
        noteDao.upsertNote(note)
        assertThat(noteDao.loadNotes().contains(note)).isTrue()
    }

    @Test
    fun deleteNote_NoteEntityIsDeleted() = runTest {
        val note = NoteEntity(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            dateAdded = System.currentTimeMillis(),
            id = 1
        )
        noteDao.upsertNote(note)
        assertThat(noteDao.loadNotes().contains(note)).isTrue()
        noteDao.deleteNote(note)
        assertThat(!noteDao.loadNotes().contains(note)).isTrue()
    }

    @Test
    fun getAllNoteEntityFromEmptyDb_noteListIsEmpty() = runTest {
        assertThat(noteDao.loadNotes().isEmpty()).isTrue()
    }

    @Test
    fun getAllNoteEntityFromDb_noteListIsNotEmpty() = runTest {
        for (i in 1..4) {
            val note = NoteEntity(
                title = "title $i",
                description = "description $i",
                imageUrl = "imageUrl $i",
                dateAdded = System.currentTimeMillis(),
                id = i
            )
            noteDao.upsertNote(note)
        }
        assertThat(noteDao.loadNotes().isNotEmpty()).isTrue()
    }
}