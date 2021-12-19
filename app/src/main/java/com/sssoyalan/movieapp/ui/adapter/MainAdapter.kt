package com.sssoyalan.movieapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sssoyalan.movieapp.R
import com.sssoyalan.movieapp.base.BaseViewHolder
import com.sssoyalan.movieapp.data.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_row.view.*

class MainAdapter(
    private val context: Context, private val moviesList: MutableList<Movie>,
    private val itemClickListener: OnMovieClickListener, private val isFavorite : Boolean
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieCLick(movie: Movie, position: Int, isDelete : Boolean)
    }

    fun deleteMovie(position: Int) : Int{
        moviesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        return itemCount
    }

    fun sortMovies(type:String){
        when(type){
            "A - Z" ->  moviesList.sortBy { it -> it.name  }
            context.getString(R.string.votes) ->  moviesList.sortByDescending { it -> it.voteAverage  }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.movies_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(moviesList[position], position)
        }
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Movie>(itemView) {
        override fun bind(item: Movie, position: Int) {
            itemView.movie_delete.visibility = if (isFavorite) View.VISIBLE else View.GONE
            Glide.with(context).load("https://image.tmdb.org/t/p/w500${item.movieImg}")
                .placeholder(R.drawable.ic_baseline_broken_image_24).fitCenter()
                .into(itemView.movie_img)
            if(item.isAdult){
                itemView.movie_adult_img.visibility =  View.VISIBLE
            }
            itemView.movie_title.text = item.name
            itemView.movie_description.text = item.description
            itemView.movie_votes_average.text = item.voteAverage.toString()
            itemView.movie_release_year.text = item.releaseDate.split("-")[0]

            itemView.setOnClickListener { itemClickListener.onMovieCLick(item, position,false) }
            itemView.movie_delete.setOnClickListener{ itemClickListener.onMovieCLick(item, position,true) }
        }
    }
}