package com.example.mindsdbkb.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInstance {

    private fun apiInstance(): Retrofit {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}
val api: ApiCall =apiInstance().create(ApiCall::class.java)
}