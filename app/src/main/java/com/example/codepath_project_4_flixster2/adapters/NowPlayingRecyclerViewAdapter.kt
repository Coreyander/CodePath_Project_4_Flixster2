package com.example.flixster

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flixster.models.NowPlayingResult

class NowPlayingRecyclerViewAdapter(private val context: Context,
                                    private val movies: MutableList<NowPlayingResult>
                                    ) : RecyclerView.Adapter<NowPlayingRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.now_playing_viewholder, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: NowPlayingResult? = null
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDateTextView)
        val overviewTextView: TextView = itemView.findViewById(R.id.overviewTextView)
    }

    //Bindings are named after NowPlayingModel and NowPlayingResult, after the Moshi API conversion from NowPlayingModel
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = movies[position]
        val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
        holder.item = model
        model.posterPath = posterBaseUrl+model.posterPath
        holder.titleTextView.text = model.title
        holder.releaseDateTextView.text = "Release Date:  " + model.releaseDate
        holder.overviewTextView.text = model.overview
        /** TODO: Explore Glide transformations --> https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations **/
        val radius = 30
        Glide.with(context)
            .load(model.posterPath)
            //.centerCrop()
            //.transform(RoundedCorners(radius))
            .placeholder(R.drawable.gator_ate_it)
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateData(data: MutableList<NowPlayingResult>) {
        movies.addAll(data)
        notifyDataSetChanged()
    }
}