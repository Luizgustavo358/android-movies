package com.rafaelguimas.movies.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.activity.MovieDetailActivity;
import com.rafaelguimas.movies.activity.MovieListActivity;
import com.rafaelguimas.movies.db.MovieDAO;
import com.rafaelguimas.movies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    
    // ID do argumento que contem os detalhes do filme
    public static final String ARG_MOVIE = "movie";

    // Flag de layout isTwoView
    public static final String ARG_IS_TWO_PANEL = "isTwoView";

    // Filme a ser exibido
    public Movie movie;

    // Flag
    public boolean isTwoPanel;

    // Views
    @BindView(R.id.layout_details) ScrollView layoutDetails;
    @BindView(R.id.layout_empty) LinearLayout layoutEmpty;
    @BindView(R.id.img_thumbnail) ImageView imgThumbnail;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_year) TextView tvYear;
    @BindView(R.id.tv_imdb_rating) TextView tvImdbRating;
    @BindView(R.id.tv_imdb_rating_max) TextView tvImdbRatingMax;
    @BindView(R.id.tv_metascore) TextView tvMetascore;
    @BindView(R.id.tv_metascore_max) TextView tvMetascoreMax;
    @BindView(R.id.tv_director) TextView tvDirector;
    @BindView(R.id.tv_plot) TextView tvPlot;
    @BindView(R.id.tv_writers) TextView tvWriters;
    @BindView(R.id.tv_actors) TextView tvActors;
    @BindView(R.id.tv_released) TextView tvReleased;
    @BindView(R.id.tv_genre) TextView tvGenre;
    @BindView(R.id.tv_rated) TextView tvRated;
    @BindView(R.id.tv_runtime) TextView tvRuntime;
    @BindView(R.id.tv_language) TextView tvLanguage;
    @BindView(R.id.tv_country) TextView tvCountry;
    @BindView(R.id.tv_awards) TextView tvAwards;
    @BindView(R.id.layout_more) GridLayout layoutMore;
    @BindView(R.id.btn_view_more) Button btnViewMore;
    @BindView(R.id.btn_delete_movie) FloatingActionButton btnDeleteMovie;

    // Obrigatorio
    public MovieDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ativa o menu
        setHasOptionsMenu(true);

        // Recupera o filme recebido nos argumentos
        if (getArguments() != null && getArguments().containsKey(ARG_MOVIE)) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }

        // Recupera a flag isTwoPanel
        if (getArguments() != null && getArguments().containsKey(ARG_IS_TWO_PANEL)) {
            isTwoPanel = getArguments().getBoolean(ARG_IS_TWO_PANEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Faz o mapeamento do layout
        ButterKnife.bind(this, rootView);

        // Exibe o layout correto
        if (movie == null) {
            // Exibe layout vazio
            showEmptyLayout();
        } else {
            // Exibe o layout de detalhes
            showDetailsLayout();

            // Define os valores do filme
            // Cabecalho
            Picasso.with(getContext())
                    .load(movie.getPoster())
                    .placeholder(R.drawable.img_movie_placeholder)
                    .into(imgThumbnail);
            tvTitle.setText(movie.getTitle());
            tvYear.setText(movie.getYear());
            tvImdbRating.setText(movie.getImdbRating());

            // Verifica se o filme possui avaliacao no IMDB
            if(movie.getImdbRating() == null || movie.getImdbRating().equals("N/A")) {
                tvImdbRating.setText("-");
                tvImdbRatingMax.setVisibility(View.GONE);
            } else {
                tvImdbRating.setText(movie.getImdbRating());
            }

            // Verifica se o filme possui avaliacao do Metascore
            if(movie.getMetascore() == null || movie.getMetascore().equals("N/A")) {
                tvMetascore.setText("-");
                tvMetascoreMax.setVisibility(View.GONE);
            } else {
                tvMetascore.setText(movie.getMetascore());
            }

                // Enredo
                tvPlot.setText(movie.getPlot());

                // Pessoas
                tvDirector.setText(movie.getDirector());
                tvWriters.setText(movie.getWriter());
                tvActors.setText(movie.getActors());

                // Outros
                tvLanguage.setText(movie.getLanguage());
                tvGenre.setText(movie.getGenre());
                tvRated.setText(movie.getRated());
                tvReleased.setText(movie.getReleased());
                tvRuntime.setText(movie.getRuntime());
                tvCountry.setText(movie.getCountry());
                tvAwards.setText(movie.getAwards());

                // Botao de exibir mais
                btnViewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnViewMore.setVisibility(View.GONE);
                        layoutMore.setVisibility(View.VISIBLE);
                    }
                });

                // Botao de remover
                btnDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteMovie();
            }
        });
        }

        return rootView;
    }

    public void showDialogDeleteMovie() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.attention)
                .setMessage(R.string.delete_movie_confirm)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Remove o filme do BD
                        MovieDAO movieDAO = new MovieDAO(getContext());
                        movieDAO.deleteMovie(movie);

                        if (!isTwoPanel) {
                            // Fecha o fragment
                            getActivity().onBackPressed();
                        } else {
                            showEmptyLayout();
                            ((MovieListActivity) getContext()).notifyAdapterRemove(movie);
                        }
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    public void showEmptyLayout() {
        layoutDetails.setVisibility(View.GONE);
        btnDeleteMovie.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
    }

    public void showDetailsLayout() {
        layoutDetails.setVisibility(View.VISIBLE);
        btnDeleteMovie.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
    }
}
