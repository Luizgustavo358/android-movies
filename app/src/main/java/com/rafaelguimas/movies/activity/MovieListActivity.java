package com.rafaelguimas.movies.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.adapter.MovieListAdapter;
import com.rafaelguimas.movies.api.OMDBClient;
import com.rafaelguimas.movies.api.OMDBInterface;
import com.rafaelguimas.movies.model.Movie;
import com.rafaelguimas.movies.model.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {
    // TAG
    private static final String TAG = "MovieListActivity";

    // Flag para indicar o tipo de visualizacao
    private boolean isTwoPanel;

    private LinearLayout layoutEmpty;
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Setup da toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_movie_list);

        // Recupera o layout de lista vazia
        layoutEmpty = (LinearLayout) findViewById(R.id.layout_empty);

        // Recupera o botao de adicionar novo filme
        Button btnAddMovie = (Button) findViewById(R.id.btn_add_movie);
        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddMovie();
            }
        });

        // Recupera o RV
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Cria o adaptador
        adapter = new MovieListAdapter(MovieListActivity.this, movieList, isTwoPanel);
        // Define o adaptador no RV
        recyclerView.setAdapter(adapter);

        // Verifica se a view de detail esta presente na tela (tablet layout)
        if (findViewById(R.id.movie_detail_container) != null) {
            // Ativa a flag de twopanel devido a view estar presente
            isTwoPanel = true;
        }

        // Busca a lista
//        getMoviesBySearch("Batman");
//        getMoviesByTitle("Batman");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void getMoviesBySearch(String searchText) {
        OMDBInterface omdbInterface = OMDBClient.getClient().create(OMDBInterface.class);
        Call<MovieList> call = omdbInterface.getMoviesBySearch(searchText);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                Log.d(TAG, "API " + (response.isSuccessful()? "success" : "error"));

                // Recupera a lista de filmes da resposta da API
                MovieList movieList = response.body();
                // Verifica se foi encontrado
                assert recyclerView != null;
                // Cria o adaptador
                MovieListAdapter adapter = new MovieListAdapter(MovieListActivity.this, movieList.getSearch(), isTwoPanel);
                // Define o adaptador no RV
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

    public void getMoviesByTitle(String title) {
        OMDBInterface omdbInterface = OMDBClient.getClient().create(OMDBInterface.class);
        Call<Movie> call = omdbInterface.getMoviesByTitle(title);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(TAG, "API " + (response.isSuccessful()? "success" : "error"));

                // Recupera o filme da resposta da API
                Movie movie = response.body();
                movieList.add(movie);
                // Verifica se foi encontrado
                assert recyclerView != null;
                // Atualiza a lista
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void showMovieByTitle(String title) {
        OMDBInterface omdbInterface = OMDBClient.getClient().create(OMDBInterface.class);
        Call<Movie> call = omdbInterface.getMoviesByTitle(title);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(TAG, "API " + (response.isSuccessful()? "success" : "error"));

                // Recupera o filme da resposta da API
                Movie movie = response.body();

                if (movie.getResponse().equals("True")) {
                    // Exibe dialog de detalhes
                    showDialogMovieDetails(movie);
                } else {
                    // Exibe mensagem de falha
                    Toast.makeText(MovieListActivity.this, R.string.movie_not_found, Toast.LENGTH_LONG).show();
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
                        // Recupera o filme da resposta da API
                        movieList.add(movie);
                        // Verifica se foi encontrado
                        assert recyclerView != null;
                        // Atualiza a lista
                        adapter.notifyDataSetChanged();
                        // Esconde o layout de lista vazia
                        layoutEmpty.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }
}
