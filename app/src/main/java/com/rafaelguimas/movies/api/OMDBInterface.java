package com.rafaelguimas.movies.api;

import com.rafaelguimas.movies.model.Movie;
import com.rafaelguimas.movies.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rafael on 29/12/2016.
 */

public interface OMDBInterface {
    @GET("?y=&plot=short&r=json")
    Call<MovieList> getMoviesBySearch(@Query("s") String searchTerm);
//    Call<MovieList> getMoviesBySearch(@Query("s") String searchTerm, @Query("page") int page);

    @GET("?y=&plot=short&r=json")
    Call<Movie> getMoviesByTitle(@Query("t") String title);
}
