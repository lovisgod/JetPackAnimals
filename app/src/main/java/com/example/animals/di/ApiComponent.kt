package com.example.animals.di

import com.example.animals.model.AnimalApiService
import com.example.animals.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService) //this tells the system where to inject the module into
}


@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
@Singleton // since we use singleton in the module then we must make the component singleton
interface ViewModelComponent {
    fun inject(viewmodel: ListViewModel)
}