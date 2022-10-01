package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codepath_project_4_flixster2.RetrofitInterface
import com.example.flixster.models.NowPlayingModel
import com.example.codepath_project_4_flixster2.navigation.Navigation
import com.example.flixster.models.NowPlayingResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class NowPlayingActivity : AppCompatActivity() {
    private var moviesNowPlaying = mutableListOf<NowPlayingResult>()
    private lateinit var moviesRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        moviesRecyclerView = findViewById(R.id.nowPlayingRecyclerView)
        val nowPlayingAdapter = NowPlayingRecyclerViewAdapter(this, moviesNowPlaying)
        moviesRecyclerView.adapter = nowPlayingAdapter
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)

        //Navigation
        val nav = Navigation()
        val scrollBtn = findViewById<Button>(R.id.scrollRightBtn)
        scrollBtn.setOnClickListener {
            nav.navToTrending(this)
        }

        //JSON to Kotlin conversion
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //Get request service
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(RetrofitInterface.BASE_URL)
            .build()

        //API call
        val api = retrofit.create(RetrofitInterface::class.java)
        val call = api.getMoviesNowPlaying()
        //call start
        call.enqueue(object : Callback<NowPlayingModel> {
            override fun onResponse(
                call: Call<NowPlayingModel>,
                response: Response<NowPlayingModel>
            ) {
                Log.i("Now Playing Activity", response.toString())
                moviesNowPlaying = response.body()?.results!! as MutableList<NowPlayingResult>
                nowPlayingAdapter.updateData(moviesNowPlaying)
            }

            override fun onFailure(call: Call<NowPlayingModel>, t: Throwable) {
                Log.e("Now Playing Activity", call.toString())
            }
        })
        //call end
    }

}
