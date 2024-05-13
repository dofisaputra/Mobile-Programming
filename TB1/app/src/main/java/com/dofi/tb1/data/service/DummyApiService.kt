package com.dofi.tb1.data.service

import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.Post
import com.dofi.tb1.data.model.User
import com.dofi.tb1.data.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApiService {

    @GET("user")
    suspend fun getUser(
        @Query("limit") limit: Int
    ): Response<BaseResponse<User>>

    @GET("user/{id}")
    suspend fun getDetailUser(
        @Path("id") id: String
    ): Response<User>

    @GET("post")
    suspend fun getPost(
        @Query("limit") limit: Int
    ): Response<BaseResponse<Post>>

    @GET("user/{id}/post")
    suspend fun getPostByUser(
        @Path("id") id: String,
        @Query("limit") limit: Int
    ): Response<BaseResponse<Post>>

    @GET("comment")
    suspend fun getComment(
        @Query("limit") limit: Int
    ): Response<BaseResponse<Comment>>

    @GET("post/{id}/comment")
    suspend fun getCommentByPost(
        @Path("id") id: String,
        @Query("limit") limit: Int
    ): Response<BaseResponse<Comment>>

}