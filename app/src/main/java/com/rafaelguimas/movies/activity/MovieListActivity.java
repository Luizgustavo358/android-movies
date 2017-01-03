package com.rafaelguimas.movies.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.Util;
import com.rafaelguimas.movies.adapter.MovieListAdapter;
import com.rafaelguimas.movies.api.OMDBClient;
import com.rafaelguimas.movies.api.OMDBInterface;
import com.rafaelguimas.movies.db.MovieDAO;
import com.rafaelguimas.movies.fragment.MovieDetailFragment;
import com.rafaelguimas.movies.model.Movie;
import com.rafaelguimas.movies.model.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    // TAG para logs
    private static final String TAG = "MovieListActivity";

    // Flag para indicar o tipo de visualizacao
    private boolean isTwoPanel;

    // Quantidades de colunas
    private int tabletColumns = 5;
    private int phonePortraitColumns = 3;
    private int phoneLandscapeColumns = 5;

    // Views
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.layout_empty) LinearLayout layoutEmpty;
    @BindView(R.id.btn_add_movie) Button btnAddMovie;
    @BindView(R.id.rv_movie_list) RecyclerView recyclerView;

    // Variaveis de controle
    private MovieListAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Conecta os elementos da tela
        ButterKnife.bind(this);

        // Setup da toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_movie_list);
        setSupportActionBar(toolbar);

        // Verifica se a view de detail esta presente na tela (tablet layout)
        if (findViewById(R.id.movie_detail_container) != null) {
            // Ativa a flag de twopanel devido a view estar presente
            isTwoPanel = true;
        }

        // Exibe layout de detalhes vazio
        if (isTwoPanel) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, new MovieDetailFragment())
                    .commit();
        }

        // Recupera o botao de adicionar novo filme
        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddMovie();
            }
        });

        // Define o layout da RV
        recyclerView.setLayoutManager(new GridLayoutManager(this, isTwoPanel? tabletColumns : phonePortraitColumns));

        // Cria o adaptador
        adapter = new MovieListAdapter(MovieListActivity.this, movieList, isTwoPanel);
        // Define o adaptador no RV
        recyclerView.setAdapter(adapter);

        // Carrega os filmes do BD local
        showMovies();

//        saveMoviesBySearch("Batman");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu da tela
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showDialogAddMovie();
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Verifica o layout
        if (!isTwoPanel) {
            int columns;

            // Verifica a orientacao do celular (Portrait/Landscape)
            if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0) { // Portrait
                columns = phonePortraitColumns;
            } else { // Landscape
                columns = phoneLandscapeColumns;
            }

            // Define o layout da RV
            recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        }

        // Guarda o tamanho da lista antes de atualizar
        final List<Movie> oldMovieList = new ArrayList<>();
        oldMovieList.addAll(movieList);

        // Atualiza a lista
        showMovies();

        // Verifica se alguma remocao foi feita
        if (movieList.size() < oldMovieList.size()) {
            // Exibe mensagem de sucesso na remocao
            Snackbar.make(coordinatorLayout, R.string.movie_del_success, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Busca os filmes deletados
                            Movie deletedMovie = Util.getDeletedMovie(movieList, oldMovieList);

                            // Verifica se foi encontrado o filme deletado
                            if (deletedMovie != null) {
                                // Readiciona o filme deletado
                                MovieDAO movieDAO = new MovieDAO(MovieListActivity.this);
                                movieDAO.saveMovie(deletedMovie);

                                // Exibe mensagem de sucesso
                                Snackbar.make(coordinatorLayout, R.string.movie_recover_success, Snackbar.LENGTH_SHORT).show();

                                // Atualiza a lista
                                showMovies();
                            }
                        }
                    })
                    .show();
        }
    }

    public void showMovieByTitle(String title) {
        OMDBInterface omdbInterface = OMDBClient.getClient().create(OMDBInterface.class);
        Call<Movie> call = omdbInterface.getMoviesByTitle(title);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(TAG, "API " + (response.isSuccessful()? "success" : "error"));
//                Util.dismissProgressDialog();

                // Recupera o filme da resposta da API
                Movie movie = response.body();

                if (movie.getResponse().equals("True")) {
                    // Exibe dialog de detalhes
                    showDialogMovieDetails(movie);
                } else {
                    // Exibe mensagem de falha
                    Snackbar.make(coordinatorLayout, R.string.movie_not_found, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void showDialogAddMovie() {
        // Busca a view do dialog
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_add, null);

        // Cria o dialog e exibe
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog_add)
                .setView(view)
                .setPositiveButton(R.string.action_search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Util.showProgressDialog(MovieListActivity.this, getString(R.string.searching_movie));
                        showMovieByTitle(((EditText) view.findViewById(R.id.etxt_movie_title)).getText().toString());
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }

    public void showDialogMovieDetails(final Movie movie) {
        // Busca a view do dialog
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_details, null);

        // Exibe os dados do filme
        ((TextView) view.findViewById(R.id.tv_title)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.tv_director)).setText(movie.getDirector());
        ((TextView) view.findViewById(R.id.tv_year)).setText(movie.getYear());
        Picasso.with(this)
                .load(movie.getPoster())
                .placeholder(R.drawable.img_movie_placeholder)
                .into(((ImageView) view.findViewById(R.id.img_thumbnail)));

        // Cria o dialog e exibe
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog_details)
                .setView(view)
                .setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Verifica se o filme ja esta na biblioteca
                        if (!Util.movieListContainsMovie(movieList, movie)) {
                            // Salva o filme novo no BD
                            MovieDAO movieDAO = new MovieDAO(MovieListActivity.this);
                            movieDAO.saveMovie(movie);

                            // Adiciona o filme novo na lista
                            movieList.add(movie);
                            // Atualiza a lista
//                            adapter.notifyItemInserted(movieList.size() - 1);
                            adapter.notifyDataSetChanged();


                            // Esconde o layout de lista vazia
                            if (layoutEmpty.getVisibility() == View.VISIBLE) {
                                layoutEmpty.setVisibility(View.GONE);
                            }

                            // Exibe mensagem de sucesso
                            Snackbar.make(coordinatorLayout, R.string.movie_add_success, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.show, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Rola a tela para a ultima posicao da lista de filmes
                                            recyclerView.smoothScrollToPosition(movieList.size()-1);
                                        }
                                    })
                                    .show();
                        } else {
                            // Exibe mensagem de sucesso
                            Snackbar.make(coordinatorLayout, R.string.movie_duplicated, Snackbar.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }

    public void showMovies() {
        // Busca os filmes no BD local
        MovieDAO movieDAO = new MovieDAO(this);
        movieList.clear();
        movieList.addAll(movieDAO.loadMovies());

        // Esconde/Exibe layout de lista vazia
        layoutEmpty.setVisibility(movieList.size() > 0? View.GONE : View.VISIBLE);

        // Atualiza a lista
        adapter.notifyDataSetChanged();
    }

    public void notifyAdapterRemove(Movie movie) {
        // Mostra a animacao de exclusao
        adapter.notifyItemRemoved(movieList.indexOf(movie));
        // Remove o filme da lista
        movieList.remove(movie);
    }
}
