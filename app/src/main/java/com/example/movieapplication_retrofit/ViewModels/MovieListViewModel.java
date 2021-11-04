package com.example.movieapplication_retrofit.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.Repositories.MovieRepositories;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepositories movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepositories.getInstance();
    }

    public LiveData<List<MovieModel>> getmMovies() {
        return movieRepository.getMovies();
    }

    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }


}
