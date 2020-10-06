package com.example.animals

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animals.di.AppModule
import com.example.animals.di.DaggerViewModelComponent
import com.example.animals.model.Animal
import com.example.animals.model.AnimalApiService
import com.example.animals.model.ApiKey
import com.example.animals.util.SharedPrefHelper
import com.example.animals.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalService: AnimalApiService

    @Mock
    lateinit var prefs: SharedPrefHelper

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application, test = true)

    private  var key = "test key"


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        /**Create a test component to be used by the test ListViewModel**/

      DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Test
    fun testAnimalFetchSuccess() {
        // here we are returning test key when we call the the getApikey
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal = Animal("cow",
            null,
            null,
            null,
            null,
            null,
            null)

        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun testAnimalFetchFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        // the testSingle is suppose to return list of animals but
        // it will return throwable
        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("OK", key))

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)
        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animals.value)

    }

    @Test
    fun getKeysucess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val apiKey  = ApiKey("OK", key)
        val keysingle = Single.just(apiKey)

        Mockito.`when`(animalService.getApiKey()).thenReturn(keysingle)

        val animal = Animal("cow",
            null,
            null,
            null,
            null,
            null,
            null)

        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh()
        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)

    }

    @Test
    fun getKeyFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val apiKey  = ApiKey("OK", key)
        val keysingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalService.getApiKey()).thenReturn(keysingle)
        listViewModel.refresh()
        Assert.assertEquals(null, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)

    }

    /**
     * This function helps to create new thread and
     * main thread and have it return immediately.
     * it also helps us to test an implementation that has Rx java**/
    @Before
    fun setRxSchedulers() {
        val immediate = object: Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler
                    .ExecutorWorker(Executor { it.run() }, true)
            }

        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}