package com.example.animals.model

import com.example.animals.di.DaggerApiComponent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * the gsonconverter factory converts the Json that we get from backend
 * to our model class
 *
 * The Rxjava call adapter factory converts the responses into singletons that
 * are observable**/

class AnimalApiService {

    @Inject  // this injects the api class
    lateinit var API : AnimalApi

    init {
      DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return API.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return  API.getAnimals(key)
    }
}