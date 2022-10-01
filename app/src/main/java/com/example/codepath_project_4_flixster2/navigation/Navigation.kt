package com.example.codepath_project_4_flixster2.navigation

import android.content.Context
import android.content.Intent
import com.example.flixster.NowPlayingActivity
import com.example.flixster.TRENDING_EXTRA
import com.example.flixster.TrendingDetailActivity
import com.example.flixster.TrendingNowActivity
import com.example.flixster.trendingmodel.TrendingResult

class Navigation {
    fun navToNowPlaying(context: Context) {
        val intent = Intent(context, NowPlayingActivity::class.java)
        context.startActivity(intent)
    }

    fun navToTrending(context: Context) {
        val intent = Intent(context, TrendingNowActivity::class.java)
        context.startActivity(intent)
    }

    fun navToTrendingDetail(context: Context, data: TrendingResult) {
        val intent = Intent(context, TrendingDetailActivity::class.java)
        intent.putExtra(TRENDING_EXTRA, data)
    }
}
