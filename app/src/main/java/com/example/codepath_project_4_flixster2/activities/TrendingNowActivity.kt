package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codepath_project_4_flixster2.RetrofitInterface
import com.example.codepath_project_4_flixster2.navigation.Navigation
import com.example.flixster.trendingmodel.TrendingModel
import com.example.flixster.trendingmodel.TrendingResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class TrendingNowActivity : AppCompatActivity(){
    private var moviesTrendingNow = mutableListOf<TrendingResult>()
    private lateinit var moviesRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        moviesRecyclerView = findViewById(R.id.trendingNowRecyclerView)
        val trendingAdapter = TrendingNowRecyclerViewAdapter(this, moviesTrendingNow)
        moviesRecyclerView.adapter = trendingAdapter
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)

        //Navigation
        val nav = Navigation()
        val scrollBtn = findViewById<Button>(R.id.scrollLeftBtn)
        scrollBtn.setOnClickListener {
            nav.navToNowPlaying(this)
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
        val call = api.getMoviesTrending()
        //call start
        call.enqueue(object: Callback<TrendingModel>{
            override fun onResponse(call: Call<TrendingModel>, response: Response<TrendingModel>) {
                Log.i("Trending Activity", response.toString())
                moviesTrendingNow = response.body()?.results!! as MutableList<TrendingResult>
                trendingAdapter.updateData(moviesTrendingNow)
            }

            override fun onFailure(call: Call<TrendingModel>, t: Throwable) {
                Log.e("Trending Activity", call.toString())
            }
        })
        //call end
    }

}