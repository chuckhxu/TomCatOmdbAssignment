package com.example.tomcat_omdbassignment

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "Poster") val poster: String
)
