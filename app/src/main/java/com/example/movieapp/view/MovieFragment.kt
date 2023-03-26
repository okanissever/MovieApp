package com.example.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.viewModel.MovieViewModel
import java.util.*
import kotlin.collections.ArrayList

class MovieFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter : MovieAdapter
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.downloadData()
        observeLiveData()

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


    }
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Movie>()
            for (i in viewModel.movieList.value!!) {
                if (i.title?.lowercase(Locale.ROOT)!!.contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
               println("okan")
            } else {
                movieAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun observeLiveData(){
        viewModel.movieList.observe(viewLifecycleOwner){movies->
            binding.movieRecyclerView.visibility = View.VISIBLE
            movieAdapter = MovieAdapter(movies)
            binding.movieRecyclerView.adapter = movieAdapter

            movieAdapter.onMovieClick = {
                val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }

        }
        viewModel.movieError.observe(viewLifecycleOwner){error->
            error?.let {
                if(it){
                    binding.movieError.visibility = View.VISIBLE
                }
                else{
                    binding.movieError.visibility = View.GONE
                }
            }
        }
        viewModel.movieLoading.observe(viewLifecycleOwner){loading->
            loading?.let {
                if(it){
                    binding.movieLoading.visibility = View.VISIBLE
                    binding.movieError.visibility = View.GONE
                    binding.movieRecyclerView.visibility = View.GONE
                }
                else{
                    binding.movieLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}