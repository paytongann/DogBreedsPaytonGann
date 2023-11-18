package com.example.dogbreeds.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbreeds.model.BreedImages
import com.example.dogbreeds.model.DogsInterface
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DOG_CEO_URL = "https://dog.ceo/"

class SelectedBreedViewModel: ViewModel()  {

    var errorStatus: Boolean? by mutableStateOf(null)

    @SuppressLint("CheckResult")
    fun loadBreedImages(breedName: String): LiveData<BreedImages> {
        val imageData = MutableLiveData<BreedImages>()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(DOG_CEO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val factInterface: DogsInterface = retrofit.create(DogsInterface::class.java)
        factInterface.getDogBreedImages(breedName)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    imageData.value = it
                    errorStatus = false
                    Log.d("TAG", "image data success $it")
                },
                { e ->
                    errorStatus = true
                    Log.d("TAG", "image data error $e")
                }
            )
        return imageData
    }

}