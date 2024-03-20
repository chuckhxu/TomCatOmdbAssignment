package com.example.tomcat_omdbassignment

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "Search") val movies: List<Movie>
)
