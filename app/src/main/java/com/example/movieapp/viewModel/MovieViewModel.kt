package com.example.movieapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.Constants
import com.example.movieapp.model.Movie
import com.example.movieapp.service.ApiClient
import com.example.movieapp.service.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel : ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    val movieLoading = MutableLiveData<Boolean>()
    val movieError= MutableLiveData<Boolean>()
    var job : Job? = null
    val apiClient = ApiClient()
    val client = apiClient.getClient()

    fun downloadData(){
        val api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieApiService::class.java)

            job = viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main){
                    movieLoading.value = true
                }
                try {
                    val response = api.getMovies()
                    withContext(Dispatchers.Main){
                        if(response.isSuccessful){
                            response.body().let {
                                movieList.value = it!!.results
                            }
                        }
                        else{
                            movieError.value = true
                        }
                        movieLoading.value = false
                    }

                }
                catch (e:Exception){
                    withContext(Dispatchers.Main){
                        println("Exception type: ${e::class.simpleName}")
                        println("Exception message: ${e.message}")
                        e.printStackTrace()
                        movieError.value = true
                        movieLoading.value = false
                    }
                }

            }

    }

}