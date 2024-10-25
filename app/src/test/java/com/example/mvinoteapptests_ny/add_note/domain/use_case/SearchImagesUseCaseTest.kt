package com.example.mvinoteapptests_ny.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteapptests_ny.add_note.presentation.util.Resource
import com.example.mvinoteapptests_ny.core.data.repositoryrepository.FakeImagesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchImagesUseCaseTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var searchImages: SearchImagesUseCase

    @Before
    fun setUp() {
        fakeImagesRepository = FakeImagesRepository()
        searchImages = SearchImagesUseCase(fakeImagesRepository)
    }

    @Test
    fun `search images with empty query, return error`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data?.images == null).isTrue()
                }

                is Resource.Success -> Unit
                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query but with network error, return error`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)

        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data?.images == null).isTrue()
                }

                is Resource.Success -> Unit
                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query, return success`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Success -> {
                    assertThat(result.data?.images != null).isTrue()
                }

                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query, list is not empty`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Success -> {
                    assertThat(result.data?.images?.size!! > 0).isTrue()
                }

                is Resource.Loading -> Unit

            }
        }
    }

}