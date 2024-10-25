package com.example.mvinoteapptests_ny.core.domain.repository

import com.example.mvinoteapptests_ny.core.domain.model.Images

interface ImagesRepository {
    suspend fun searchImages(query: String): Images?
}