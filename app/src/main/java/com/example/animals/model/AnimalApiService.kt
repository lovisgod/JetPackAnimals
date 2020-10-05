package com.example.animals.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * the gsonconverter factory converts the Json that we get from backend
 * to our model class
 *
 * The Rxjava call adapter factory converts the responses into singletons that
 * are observable**/

class AnimalApiService {
    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"
    private val API = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey(): Single<ApiKey> {
        return API.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return  API.getAnimals(key)
    }
}