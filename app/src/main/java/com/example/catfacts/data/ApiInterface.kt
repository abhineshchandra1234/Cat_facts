package com.example.catfacts.data

import com.example.catfacts.models.CatFacts
import okhttp3.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/fact")
    suspend fun getRandomFact(): retrofit2.Response<CatFacts>
}