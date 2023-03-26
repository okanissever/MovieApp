package com.example.movieapp.service

import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getMovies(): Response<MovieResponse>
}