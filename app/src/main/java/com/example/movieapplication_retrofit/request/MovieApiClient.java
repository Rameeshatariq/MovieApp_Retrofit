package com.example.movieapplication_retrofit.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapplication_retrofit.AppExecutors;
import com.example.movieapplication_retrofit.Model.MovieModel;
import com.example.movieapplication_retrofit.Utils.Credentials;
import com.example.movieapplication_retrofit.response.MovieSerachResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    public MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }
    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber){
        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future myHandler= AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                //Retrieving data

            }
        });

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancel request
                myHandler.cancel(true);

            }
        }, 5000, TimeUnit.MICROSECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int page;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getMovies(query, page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSerachResponse)response.body()).getMovies());

                    if(page == 1){
                        //postvalue for background thread
                        //setvalue for not background thread
                        mMovies.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.d("TAG", "Error: "+error);
                    mMovies.postValue(null);
                }

            }catch (Exception e){
                Log.d("TAG", "Catch: "+e);
                mMovies.postValue(null);
            }




        }
             private Call<MovieSerachResponse> getMovies(String query, int pageNumber){
                return Service.getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        query,
                        pageNumber);
            }

            private void cancelRequest(){
                Log.d("TAG", "cancelRequest: ");
                cancelRequest = true;
            }

    }
}
