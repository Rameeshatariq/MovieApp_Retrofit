package com.example.movieapplication_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapplication_retrofit.Adapters.MovieRecyclerView;
import com.example.movieapplication_retrofit.Adapters.OnMovieListener;
import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.Utils.Credentials;
import com.example.movieapplication_retrofit.ViewModels.MovieListViewModel;
import com.example.movieapplication_retrofit.request.MovieApi;
import com.example.movieapplication_retrofit.request.Service;
import com.example.movieapplication_retrofit.response.MovieSerachResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    Button btn;
    RecyclerView recyclerView;
    MovieRecyclerView adapter;
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveAnyChange();
        ConfigureRecyclerView();

        searchMovieApi("fast", 1);


    }

    private void ObserveAnyChange(){
        movieListViewModel.getmMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.d("TAG", "onChanged: "+movieModel.getTitle());
                        adapter.setmMovies(movieModels);
                    }
                }

            }
        });
    }

    private  void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);

    }
    private void ConfigureRecyclerView(){
        adapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {

    }
    /*private void GetRetrofitResponse() {
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


    private void GetMovieById(){
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                550,
                Credentials.API_KEY
        );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200){
                    Log.d("TAG", "Response"+response.body().toString());

                    MovieModel movie = response.body();


                        Log.d("TAG", "Title: "+movie.getTitle());


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
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }*/
}