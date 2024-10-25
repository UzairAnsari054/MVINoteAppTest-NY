package com.example.mvinoteapptests_ny.core.data.mapper

import com.example.mvinoteapptests_ny.core.data.remote.dto.ImageListDto
import com.example.mvinoteapptests_ny.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { it.previewURL ?: "" } ?: emptyList()
    )
}