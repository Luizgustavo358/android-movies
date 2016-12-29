package com.rafaelguimas.movies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.rafaelguimas.movies.R;
import com.rafaelguimas.movies.adapter.MovieListAdapter;
import com.rafaelguimas.movies.dummy.DummyContent;

public class MovieListActivity extends AppCompatActivity {

    // Flag para indicar o tipo de visualizacao
    private boolean isTwoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Recupera o RV
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        // Verifica se foi encontrado
        assert recyclerView != null;
        // Cria o adaptador
        MovieListAdapter adapter = new MovieListAdapter(this, DummyContent.ITEMS, isTwoPanel);
        // Define o adaptador no RV
        recyclerView.setAdapter(adapter);

        // Verifica se a view de detail esta presente na tela (tablet layout)
        if (findViewById(R.id.movie_detail_container) != null) {
            // Ativa a flag de twopanel devido a view estar presente
            isTwoPanel = true;
        }
    }
}
