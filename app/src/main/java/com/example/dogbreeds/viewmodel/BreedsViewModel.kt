package com.example.dogbreeds.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbreeds.model.Breeds
import com.example.dogbreeds.model.DogsInterface
import com.example.dogbreeds.model.Message
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DOG_CEO_URL = "https://dog.ceo/"

class BreedsViewModel: ViewModel() {

    var dogList: List<String> by mutableStateOf(emptyList())
    var dogListFiltered: List<String> by mutableStateOf(emptyList())
    var errorStatus: Boolean? by mutableStateOf(null)

    @SuppressLint("CheckResult")
    fun loadDogBreeds(): LiveData<Breeds> {
        val factsData = MutableLiveData<Breeds>()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(DOG_CEO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val factInterface: DogsInterface = retrofit.create(DogsInterface::class.java)
        factInterface.getDogBreedListData()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    factsData.value = it
                    errorStatus = false
                    Log.d("TAG", "breed list success $it")
                },
                { e ->
                    errorStatus = true
                    Log.d("TAG", "breed list error $e")
                }
            )
        return factsData
    }

    //This removes sub breeds of the dog in response and capitalizes first letter.
    fun parseSubBreedsDogList(listResponse: Message) {
        val dogList = mutableListOf<String>()
        listResponse.toString().split("(", "=[], ").forEachIndexed { index, dog ->
            val breedParsed = dog.replaceAfter("=","")
                .replace("=", "")
                .replaceFirstChar(Char::titlecase)
            if (index != 0) {
                dogList.add(breedParsed)
            }
        }
        this.dogList = dogList
    }
}