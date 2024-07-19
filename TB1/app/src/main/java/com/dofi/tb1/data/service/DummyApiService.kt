package com.dofi.tb1.data.service

import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApiService {

    // SECTION: User
    @GET("user")
    suspend fun getListOfUser(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<User>>

    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<User>

    @POST("user/create")
    suspend fun createUser(
        @Body body: User
    ): Response<User>

    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body body: User
    ): Response<User>

    @DELETE("user/{id}")
    suspend fun deleteUser(
        @Path("id") id: String
    ): Response<String>

    // SECTION: Post
    @GET("post")
    suspend fun getListOfPost(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Post>>

    @GET("post/{id}")
    suspend fun getPostById(
        @Path("id") id: String
    ): Response<Post>

    @GET("user/{id}/post")
    suspend fun getPostByUser(
        @Path("id") id: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Post>>

    @GET("tag/{id}/post")
    suspend fun getPostByTag(
        @Path("id") id: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Post>>

    @POST("post/create")
    suspend fun createPost(
        @Body body: Post
    ): Response<Post>

    @PUT("post/{id}")
    suspend fun updatePost(
        @Path("id") id: String,
        @Body body: Post
    ): Response<Post>

    @DELETE("post/{id}")
    suspend fun deletePost(
        @Path("id") id: String
    ): Response<String>

    // SECTION: Comment
    @GET("comment")
    suspend fun getListOfComments(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Comment>>

    @GET("post/{id}/comment")
    suspend fun getCommentByPost(
        @Path("id") id: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Comment>>

    @GET("user/{id}/comment")
    suspend fun getCommentByUser(
        @Path("id") id: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<Comment>>

    @POST("comment/create")
    suspend fun createComment(
        @Body body: Comment
    ): Response<Comment>

    @DELETE("comment/{id}")
    suspend fun deleteComment(
        @Path("id") id: String
    ): Response<String>

    // SECTION: Tag
    @GET("tag")
    suspend fun getListOfTags(
    ): Response<BaseResponse<String>>


}