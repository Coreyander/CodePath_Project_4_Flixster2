package com.example.codepath_project_4_flixster2



import com.example.flixster.BuildConfig
import com.example.flixster.models.NowPlayingModel
import com.example.flixster.trendingmodel.TrendingModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/3/movie/now_playing")
    fun getMoviesNowPlaying(@Query("api_key") API_KEY: String = BuildConfig.API_KEY
    ) : Call<NowPlayingModel>


    @GET("/3/trending/movie/week")
    fun getMoviesTrending(@Query("api_key") API_KEY: String = BuildConfig.API_KEY
    ) : Call<TrendingModel>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }
}