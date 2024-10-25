package com.example.mvinoteapptests_ny.core.di

import android.app.Application
import androidx.room.Room
import com.example.mvinoteapptests_ny.add_note.domain.use_case.SearchImagesUseCase
import com.example.mvinoteapptests_ny.add_note.domain.use_case.UpsertNoteUseCase
import com.example.mvinoteapptests_ny.core.data.local.NoteDatabase
import com.example.mvinoteapptests_ny.core.data.remote.api.ImagesApi
import com.example.mvinoteapptests_ny.core.data.repository.ImagesRepositoryImpl
import com.example.mvinoteapptests_ny.core.data.repository.NoteRepositoryImpl
import com.example.mvinoteapptests_ny.core.domain.repository.ImagesRepository
import com.example.mvinoteapptests_ny.core.domain.repository.NoteRepository
import com.example.mvinoteapptests_ny.note_list.domain.use_case.DeleteNoteUseCase
import com.example.mvinoteapptests_ny.note_list.domain.use_case.LoadNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "Note_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database)
    }

    @Singleton
    @Provides
    fun provideLoadNotesUseCase(noteRepository: NoteRepository): LoadNotesUseCase {
        return LoadNotesUseCase(noteRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }


    @Singleton
    @Provides
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImagesApi.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchImagesRepository(imagesApi: ImagesApi): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }

    @Singleton
    @Provides
    fun provideUpsertNoteUseCase(noteRepository: NoteRepository): UpsertNoteUseCase {
        return UpsertNoteUseCase(noteRepository)
    }

}