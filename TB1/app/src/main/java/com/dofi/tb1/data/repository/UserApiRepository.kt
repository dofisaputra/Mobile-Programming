package com.dofi.tb1.data.repository

import com.dofi.tb1.data.model.UserLogin
import com.dofi.tb1.data.service.UserApiService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserApiRepository(
    private val service: UserApiService
) {

    suspend fun getUser(id: String): Flow<Response<UserLogin>> =
        flow {
            emit(service.getUser(id))
        }

    suspend fun createUser(body: UserLogin): Flow<Response<UserLogin>> =
        flow {
            emit(service.createUser(body))
        }

}