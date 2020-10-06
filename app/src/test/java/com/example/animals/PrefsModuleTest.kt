package com.example.animals

import android.app.Application
import com.example.animals.di.PrefsModule
import com.example.animals.util.SharedPrefHelper

class PrefsModuleTest(val mockPrefs: SharedPrefHelper): PrefsModule() {

    override fun provideSharedPrefs(application: Application): SharedPrefHelper {
        return mockPrefs
    }


}