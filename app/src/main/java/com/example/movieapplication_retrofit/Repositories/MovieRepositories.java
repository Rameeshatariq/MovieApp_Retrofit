package com.example.movieapplication_retrofit.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.request.MovieApiClient;

import java.util.List;

public class MovieRepositories {

    private static MovieRepositories instance;
    private MovieApiClient movieApiClient;

    public static MovieRepositories getInstance(){
        if(instance == null){
            instance = new MovieRepositories();
        }
        return instance;
    }
    private MovieRepositories(){

        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return  movieApiClient.getMovies();
    }

    public void searchMovieApi(String query, int pageNumber){
        movieApiClient.searchMoviesApi(query,pageNumber);

    }
}
