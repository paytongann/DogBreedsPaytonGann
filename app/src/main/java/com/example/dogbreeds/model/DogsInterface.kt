package com.example.dogbreeds.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsInterface {

    //https://dog.ceo/api/breeds/list/all
    @GET("api/breeds/list/all")
    fun getDogBreedListData(): Observable<Breeds>


    //https://dog.ceo/api/breed/hound/images
    @GET("/api/breed/{name}/images")
    fun getDogBreedImages(
        @Path("name") dogBreedName: String
    ): Observable<BreedImages>
}