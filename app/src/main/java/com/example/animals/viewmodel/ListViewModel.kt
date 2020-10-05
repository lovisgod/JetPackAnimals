package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animals.model.Animal
import com.example.animals.model.AnimalApiService
import com.example.animals.model.ApiKey
import com.example.animals.util.SharedPrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application): AndroidViewModel(application) { // the reason for using android viewModel and not just viewModel is to be able to access context

    val animals by lazy { MutableLiveData<List<Animal>> () } // lazy means that the system will not instatiate the livedata variable until when it's needed.
    val loadError by lazy { MutableLiveData<Boolean> () }
    val loading by lazy { MutableLiveData<Boolean> ()}


    /**
     * when we attach to observables if the viewmodel is destroyed,
     * we will still have a link to the observable and we have a memory leak
     * we clear this links using disposables**/
    private val disposable = CompositeDisposable()

    private val api = AnimalApiService()

    private val prefs = SharedPrefHelper(getApplication())

    private var invalidApiKey = false



    fun refresh() {
        invalidApiKey = false
        loading.value = true
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    private fun getKey() {
        /**
         * the subscribe on creates a new thread for our application
         * while calling the api server
         *
         * the observeOn returns the result on the main thread
         *
         * the suscribe with is where we get the information or the error**/
        disposable.add(
            api.getApiKey().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(t: ApiKey) {
                        if (t.key.isNullOrEmpty()) {
                            loading.value = false
                            loadError.value = true
                        } else {
                            prefs.saveApiKey(t.key)
                            getAnimals(t.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }

                })
        )
    }

    private fun getAnimals(key: String) {

        disposable.add(
            api.getAnimals(key).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(t: List<Animal>) {
                        loading.value = false
                        loadError.value = false
                        animals.value = t

                    }

                    override fun onError(e: Throwable) {

                        if (!invalidApiKey) {
                            invalidApiKey = true
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            loadError.value = true
                            animals.value = null
                        }
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}