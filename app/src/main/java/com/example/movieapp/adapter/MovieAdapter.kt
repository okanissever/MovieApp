package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.common.Constants.POSTER_BASE_URL
import com.example.movieapp.databinding.ItemRowBinding
import com.example.movieapp.model.Movie

class MovieAdapter(var movieList : List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    var onMovieClick:(Movie) -> Unit = {}

    fun setFilteredList(mList: List<Movie>){
        this.movieList = mList
        notifyDataSetChanged()
    }

    class MovieHolder(val binding : ItemRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
       val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.binding.tvMovieName.text = movieList[position].title
        holder.binding.tvLang.text = movieList[position].originalLanguage
        holder.binding.tvRate.text = movieList[position].voteAverage.toString()
        holder.binding.tvMovieDateRelease.text = movieList[position].releaseDate
        val moviePosterURL = POSTER_BASE_URL+ movieList[position].posterPath
        Glide.with(holder.binding.ImgMovie).load(moviePosterURL).into(holder.binding.ImgMovie)

        holder.binding.root.setOnClickListener {
            onMovieClick(movieList[position])
        }
    }
}