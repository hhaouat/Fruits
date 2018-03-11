package com.fruits

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.Callable

interface FruitsClient {

    @GET("master/data.json")
    fun getFruit(): Call<FruitsApiResponse>
}