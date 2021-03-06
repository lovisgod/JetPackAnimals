package com.example.animals.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("getKey")
    fun getApiKey(): Single<ApiKey> // a single is an observable like livedata that can be observed

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String): Single<List<Animal>>
}