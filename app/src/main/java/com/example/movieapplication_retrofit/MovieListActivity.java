package com.example.movieapplication_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.Utils.Credentials;
import com.example.movieapplication_retrofit.request.MovieApi;
import com.example.movieapplication_retrofit.request.Service;
import com.example.movieapplication_retrofit.response.MovieSerachResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRetrofitResponse();
            }
        });
    }

    private void GetRetrofitResponse() {
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieSerachResponse> responseCall = movieApi.searchMovie(
                Credentials.API_KEY,
                "Action",
                1
        );

        responseCall.enqueue(new Callback<MovieSerachResponse>() {
            @Override
            public void onResponse(Call<MovieSerachResponse> call, Response<MovieSerachResponse> response) {
                if(response.code() == 200){
                    Log.d("TAG", "Response"+response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie : movies){
                        Log.d("TAG", "Release Date"+movie.getRelease_date());

                    }
                }
                else{
                    try{
                        Log.d("TAG", "Error"+response.errorBody().string());
                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSerachResponse> call, Throwable t) {

            }
        });
    }
}