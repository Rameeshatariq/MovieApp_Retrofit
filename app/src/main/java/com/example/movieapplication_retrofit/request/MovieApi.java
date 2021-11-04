package com.example.movieapplication_retrofit.request;

import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.response.MovieSerachResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("3/search/movie")
    Call<MovieSerachResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );
    @GET("3/movie/{movie_id}")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
            );
}
