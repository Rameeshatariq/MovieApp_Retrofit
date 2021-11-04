package com.example.movieapplication_retrofit.response;

import com.example.movieapplication_retrofit.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSerachResponse {
    @SerializedName("total_results")
    @Expose
    private int total_count;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;


    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSerachResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
