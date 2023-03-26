package com.example.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.common.Constants
import com.example.movieapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.movie.let {
            binding.apply {
                tvMovieTitle.text = it.title
                tvMovieDateRelease.text = it.releaseDate
                tvMovieRating.text = it.voteAverage.toString()
                tvMoviePopularityText.text = it.popularity.toString()
                tvMovieOverview.text = it.overview
                val moviePosterURL = Constants.POSTER_BASE_URL + it.posterPath
                Glide.with(imgMovie).load(moviePosterURL).into(imgMovie)
                Glide.with(imgMovieBack).load(moviePosterURL).into(imgMovieBack)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}