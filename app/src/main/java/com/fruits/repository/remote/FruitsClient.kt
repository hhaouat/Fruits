package com.fruits.repository.remote

import com.fruits.BuildConfig
import com.fruits.tracking.LoggingInterceptor
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FruitsClient {

    @GET("master/data.json")
    fun getFruit(): Call<FruitsApiResponse>

    @GET("master/data.json")
    fun getSingleFruitApiResponse(): Single<FruitsApiResponse>

    @GET("master/stats")
    fun sentEventLoad(@Query("event")  event : String, @Query("data")  data: Int): Call<Void>

    @GET("master/stats")
    fun sentEventError(@Query("event")  event : String, @Query("error")  data: String): Call<Void>

    @GET("master/stats")
    fun sentEventDisplay(@Query("event")  event : String, @Query("display")  data: String): Call<Void>

    companion object {

        fun create() : FruitsClient {
            val gson = GsonBuilder().create()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createOkHttpClient())
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(FruitsClient::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient? {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addNetworkInterceptor(LoggingInterceptor())
                    .build()

            return client
        }
    }

}