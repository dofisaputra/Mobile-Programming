package com.dofi.tb1.di

import com.dofi.tb1.data.repository.DummyApiRepository
import com.dofi.tb1.data.repository.UserApiRepository
import com.dofi.tb1.data.service.DummyApiService
import com.dofi.tb1.data.service.UserApiService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named(RetrofitQualifier.DUMMY_API)) {
        Retrofit.Builder()
            .baseUrl("https://dummyapi.io/data/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request()
                            .newBuilder()
                            .addHeader("app-id", "663fa249e7a9463eb72e46d3")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
    }
    single(named(RetrofitQualifier.USER_API)) {
        Retrofit.Builder()
            .baseUrl("https://ca668d50d1c4998de512.free.beeceptor.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single { get<Retrofit>(named(RetrofitQualifier.DUMMY_API)).create(DummyApiService::class.java) }
    single { get<Retrofit>(named(RetrofitQualifier.USER_API)).create(UserApiService::class.java) }
    single { DummyApiRepository(get()) }
    single { UserApiRepository(get()) }
    single { Gson() }
}

object RetrofitQualifier {
    const val DUMMY_API = "DummyAPIURL"
    const val USER_API = "UserAPIURL"
}