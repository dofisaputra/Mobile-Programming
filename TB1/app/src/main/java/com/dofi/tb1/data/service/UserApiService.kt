package com.dofi.tb1.data.service

import com.dofi.tb1.data.model.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @GET("api/users/{id}")
    suspend fun getUser(
        @Path("id") id: String
    ): Response<UserLogin>

    @POST("api/users")
    suspend fun createUser(
        @Body body: UserLogin
    ): Response<UserLogin>

}