package com.example.mvinoteapptests_ny.add_note.domain.use_case

import com.example.mvinoteapptests_ny.add_note.presentation.util.Resource
import com.example.mvinoteapptests_ny.core.domain.model.Images
import com.example.mvinoteapptests_ny.core.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val searchImagesRepository: ImagesRepository
) {

    suspend operator fun invoke(query: String): Flow<Resource<Images>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            if (query.isEmpty()) {
                emit(Resource.Error())
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val images = try {
                searchImagesRepository.searchImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error())
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            images?.let {
                emit(Resource.Success(data = it))
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            emit(Resource.Error())
            emit(Resource.Loading(isLoading = false))
        }
    }
}