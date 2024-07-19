package com.dofi.tb1.data.repository

import com.dofi.tb1.data.response.image.BaseResponseImageUpload
import com.dofi.tb1.data.service.ImageApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response

class ImageApiRepository(
    private val imageApiService: ImageApiService
) {

    suspend fun uploadImage(image: MultipartBody.Part): Flow<Response<BaseResponseImageUpload>> {
        return flow {
            val response = imageApiService.uploadImage(
                key = "cb3a788b379f203c652db21bc8961cb2",
                image = image
            )
            emit(response)
        }
    }

}