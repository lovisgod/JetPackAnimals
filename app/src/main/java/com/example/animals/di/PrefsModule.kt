package com.example.animals.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.animals.util.SharedPrefHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
open class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun provideSharedPrefs(application: Application): SharedPrefHelper {
        return SharedPrefHelper(application)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPrefs(activity: AppCompatActivity): SharedPrefHelper {
        return SharedPrefHelper(activity)
    }
}

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVITY = "Activity context"

@Qualifier
annotation class TypeOfContext(val type: String) // this create a class that can work with annotation