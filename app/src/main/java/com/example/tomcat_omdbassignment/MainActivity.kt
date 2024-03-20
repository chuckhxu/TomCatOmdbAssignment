package com.example.tomcat_omdbassignment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import coil.load
import android.view.inputmethod.InputMethodManager
class MainActivity : AppCompatActivity() {
    private lateinit var api: OmdbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(OmdbApi::class.java)

        val titleInput = findViewById<EditText>(R.id.title_input)
        val searchButton = findViewById<Button>(R.id.search_button)
        val titleView = findViewById<TextView>(R.id.title_view)
        val yearView = findViewById<TextView>(R.id.year_view)
        val posterView = findViewById<ImageView>(R.id.poster_view)

        searchButton.setOnClickListener {
            val title = titleInput.text.toString()
            // hide the keyboard after clicking the search button
            titleInput.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(titleInput.windowToken, 0)


            api.searchMovie(title).enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val movie = response.body()?.movies?.firstOrNull()
                    if (movie != null) {
                        titleView.text = movie.title
                        yearView.text = movie.year
                        posterView.load(movie.poster) {
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_foreground)
                            error(R.drawable.ic_launcher_foreground)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}