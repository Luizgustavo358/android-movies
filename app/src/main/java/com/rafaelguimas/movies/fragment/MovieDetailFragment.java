package com.rafaelguimas.movies.fragment;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.activity.MovieDetailActivity;
import com.rafaelguimas.movies.activity.MovieListActivity;
import com.rafaelguimas.movies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    
    // ID do argumento que contem os detalhes do filme
    public static final String ARG_MOVIE = "movie";
    
    // Filme a ser exibido
    public Movie movie;

    // Obrigatorio
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Busca os elementos da tela
        ImageView imgPoster = (ImageView) rootView.findViewById(R.id.img_thumbnail);
        TextView tvMovieTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvMovieYear = (TextView) rootView.findViewById(R.id.tv_year);
        TextView tvImdbRating = (TextView) rootView.findViewById(R.id.tv_imdb_rating);
        TextView tvDirector = (TextView) rootView.findViewById(R.id.tv_director);

        // Define os valores do filme
        Picasso.with(getContext()).load(movie.getPoster()).into(imgPoster);
        tvMovieTitle.setText(movie.getTitle());
        tvMovieYear.setText(movie.getYear());
        tvImdbRating.setText(movie.getImdbRating());
        tvDirector.setText(movie.getDirector());

        return rootView;
    }
}
