package com.dofi.tb1.data.repository

import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.Post
import com.dofi.tb1.data.model.User
import com.dofi.tb1.data.response.BaseResponse
import com.dofi.tb1.data.service.DummyApiService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class DummyApiRepository(
    private val service: DummyApiService
) {

    suspend fun getUser(limit: Int): Flow<Response<BaseResponse<User>>> =
        flow {
            emit(service.getUser(limit))
        }

    suspend fun getDetailUser(id: String): Flow<Response<User>> =
        flow {
            emit(service.getDetailUser(id))
        }

    suspend fun getPost(limit: Int): Flow<Response<BaseResponse<Post>>> =
        flow {
            emit(service.getPost(limit))
        }

    suspend fun getPostByUser(id: String, limit: Int): Flow<Response<BaseResponse<Post>>> =
        flow {
            emit(service.getPostByUser(id, limit))
        }

    suspend fun getComment(limit: Int): Flow<Response<BaseResponse<Comment>>> =
        flow {
            emit(service.getComment(limit))
        }

    suspend fun getCommentByPost(id: String, limit: Int): Flow<Response<BaseResponse<Comment>>> =
        flow {
            emit(service.getCommentByPost(id, limit))
        }

}