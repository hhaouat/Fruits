package com.fruits.repository.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FruitsClient {

    @GET("master/data.json")
    fun getFruit(): Call<FruitsApiResponse>

    companion object {
        fun create() : FruitsClient {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createOkHttpClient())
                    .baseUrl("https://raw.githubusercontent.com/fmtvp/recruit-test-data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(FruitsClient::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient? {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(loggingInterceptor)
            return clientBuilder.build()
        }
    }

}