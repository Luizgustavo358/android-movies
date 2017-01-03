package com.rafaelguimas.movies;

import android.app.ProgressDialog;
import android.content.Context;

import com.rafaelguimas.movies.model.Movie;

import java.util.List;

/**
 * Created by Rafael on 29/12/2016.
 */

public class Util {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String title) {
        // Define o dialog de progresso caso ele nao exista
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(title);
        }

        // Exibe o dialog
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public static Movie getDeletedMovie(List<Movie> movieList, List<Movie> oldMovieList) {
        // Descobre a maior e menor lista
        List<Movie> biggerList = movieList.size() > oldMovieList.size()? movieList : oldMovieList;
        List<Movie> smallestList = movieList.size() <= oldMovieList.size()? movieList : oldMovieList;

        // Percorre a lista maior verificando qual filme ela possui a mais que a menor
        for (Movie movie: biggerList) {
            // Flag de filme encontrado
            boolean found = false;

            for (Movie movie2: smallestList) {
                if (movie.getTitle().equals(movie2.getTitle())) {
                    // Indica que o filme foi encontrado
                    found = true;
                }
            }

            // Caso nao tenha encontrado o filme, ele foi deletado
            if (!found) {
                return movie;
            }
        }

        return null;
    }

    public static boolean movieListContainsMovie(List<Movie> movieList, Movie movie) {
        // Percorre a lista verificando se ela possui o filme recebido
        for (Movie movieFromList: movieList) {
            if (movie.getTitle().equals(movieFromList.getTitle())) {
                // Indica que o filme foi encontrado
                return true;
            }
        }

        return false;
    }
}
