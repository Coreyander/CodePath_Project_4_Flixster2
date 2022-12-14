package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.trendingmodel.TrendingResult


const val TRENDING_EXTRA = "TRENDING_EXTRA"

class TrendingNowRecyclerViewAdapter(private val context: Context,
                                     private val movies: MutableList<TrendingResult>
                                     ) : RecyclerView.Adapter<TrendingNowRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.trending_now_viewholder, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var item: TrendingResult? = null
        val rankingTextView: TextView = itemView.findViewById(R.id.ranking)
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val popularityTextView: TextView = itemView.findViewById(R.id.popularityTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Get selected article
            val data = movies[adapterPosition]
            //Navigate to Details screen and pass selected article
            val intent = Intent(context, TrendingDetailActivity::class.java)
            intent.putExtra(TRENDING_EXTRA, data)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as TrendingNowActivity,
                (posterImageView as View?)!!, "poster"
            )
            context.startActivity(intent, options.toBundle())
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = movies[position]
        val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
        holder.item = model
        model.posterPath = posterBaseUrl+model.posterPath
        holder.popularityTextView.text = "Popularity Rating: " + model.popularity.toString()
        val rank = position+1
        holder.rankingTextView.text = rank.toString() + "."
        Glide.with(context)
            .load(movies[position].posterPath)
            .placeholder(R.drawable.gator_ate_it)
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateData(data: MutableList<TrendingResult>) {
        movies.addAll(data)
        movies.sortByDescending { it.popularity }
        notifyDataSetChanged()
    }


}