package com.example.flixster

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.codepath_project_4_flixster2.navigation.Navigation
import com.example.flixster.trendingmodel.TrendingResult


class TrendingDetailActivity: AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var overview: TextView
    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_detail)

        poster = findViewById(R.id.posterImageView)
        overview = findViewById(R.id.overviewTextView)
        btn = findViewById(R.id.goBackBtn)

        val trendingModel = intent.getSerializableExtra(TRENDING_EXTRA) as TrendingResult

        overview.text = trendingModel.overview


        // TODO: Load the media image
        val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
        trendingModel.posterPath = posterBaseUrl+trendingModel.posterPath
        Glide.with(this)
            .load(trendingModel.posterPath)
            .into(poster)

        val nav = Navigation()
        btn.setOnClickListener{
            nav.navToTrending(this)
        }
    }
}