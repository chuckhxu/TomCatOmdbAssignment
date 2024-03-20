package com.example.tomcat_omdbassignment

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "37ed96d4"
interface OmdbApi {
    @GET("/")
    fun searchMovie(@Query("s") title: String, @Query("apikey") apiKey: String = API_KEY): Call<MovieResponse>
}