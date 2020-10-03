package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animals.model.Animal

class ListViewModel(application: Application): AndroidViewModel(application) { // the reason for using android viewModel and not just viewModel is to be able to access context

    val animals by lazy { MutableLiveData<List<Animal>> () } // lazy means that the system will not instatiate the livedata variable until when it's needed.
    val loadError by lazy { MutableLiveData<Boolean> () }
    val loading by lazy { MutableLiveData<Boolean> ()}



    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal("aligator")
        val a2 = Animal("Dog")
        val a3 = Animal("Snake")
        val a4 = Animal("Chicken")
        val a5 = Animal("Goat")
        val a6= Animal("Fly")

        val animalList  = arrayListOf(a1, a2, a3, a4, a5, a6)
        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}