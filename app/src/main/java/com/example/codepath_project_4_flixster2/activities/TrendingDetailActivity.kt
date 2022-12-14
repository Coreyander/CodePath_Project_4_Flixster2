package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.example.codepath_project_4_flixster2.navigation.Navigation
import com.example.flixster.trendingmodel.TrendingResult


class TrendingDetailActivity: AppCompatActivity() {
    private lateinit var btn: ImageButton
    private lateinit var overview: TextView
    private lateinit var poster: ImageView
    private lateinit var voteAverage: TextView
    private lateinit var voteCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_detail)

        poster = findViewById(R.id.posterImageView)
        overview = findViewById(R.id.overviewTextView)
        voteAverage = findViewById(R.id.voteAverageTextView)
        voteCount = findViewById(R.id.voteCountTextView)
        btn = findViewById(R.id.goBackBtn)

        val trendingModel = intent.getSerializableExtra(TRENDING_EXTRA) as TrendingResult

        overview.text = trendingModel.overview
        voteAverage.text = "Average: " + trendingModel.voteAverage.toString()
        voteCount.text = "Vote Count: " + trendingModel.voteCount.toString()


        // TODO: Load the media image
        val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
        trendingModel.posterPath = posterBaseUrl+trendingModel.posterPath
        Glide.with(this)
            .load(trendingModel.posterPath)
            .into(poster)

        val nav = Navigation()
        btn.setOnClickListener{
            val intent = Intent(this, TrendingNowActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@TrendingDetailActivity,
                (poster as View?)!!, "poster"
            )
            this.startActivity(intent, options.toBundle())
        }
    }
}