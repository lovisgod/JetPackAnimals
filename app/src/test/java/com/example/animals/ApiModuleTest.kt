package com.example.animals

import com.example.animals.di.ApiModule
import com.example.animals.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService): ApiModule() {

    override fun provideApiService(): AnimalApiService {
        return mockService
    }
}