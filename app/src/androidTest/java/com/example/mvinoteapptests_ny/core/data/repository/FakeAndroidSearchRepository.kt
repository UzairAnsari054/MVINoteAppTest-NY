package com.example.mvinoteapptests_ny.core.data.repository

import com.example.mvinoteapptests_ny.core.domain.model.Images
import com.example.mvinoteapptests_ny.core.domain.repository.ImagesRepository

class FakeAndroidSearchRepository : ImagesRepository {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(query: String): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(listOf("Image1", "Image2", "Image3", "Image4", "Image5", "Image6"))
        }
    }
}