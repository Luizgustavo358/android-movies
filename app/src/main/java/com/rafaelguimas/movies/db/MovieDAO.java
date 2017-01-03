package com.rafaelguimas.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rafaelguimas.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 30/12/2016.
 */

public class MovieDAO {

    private Context context;

    public MovieDAO(Context context) {
        this.context = context;
    }

    private SQLiteDatabase getDatabase() {
        // Instancia a classe de controle de BD
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Recupera o repositorio de dados com modo escrita
        return databaseHelper.getWritableDatabase();
    }

    public void saveMovie(Movie movie) {
        // Cria o mapa dos valores
        ContentValues values = new ContentValues();
        values.put(MovieDB.COLUMN_NAME_TITLE, movie.getTitle());
        values.put(MovieDB.COLUMN_NAME_YEAR, movie.getYear());
        values.put(MovieDB.COLUMN_NAME_RATED, movie.getRated());
        values.put(MovieDB.COLUMN_NAME_RELEASED, movie.getReleased());
        values.put(MovieDB.COLUMN_NAME_RUNTIME, movie.getRuntime());
        values.put(MovieDB.COLUMN_NAME_GENRE, movie.getGenre());
        values.put(MovieDB.COLUMN_NAME_DIRECTOR, movie.getDirector());
        values.put(MovieDB.COLUMN_NAME_WRITER, movie.getWriter());
        values.put(MovieDB.COLUMN_NAME_ACTORS, movie.getActors());
        values.put(MovieDB.COLUMN_NAME_PLOT, movie.getPlot());
        values.put(MovieDB.COLUMN_NAME_LANGUAGE, movie.getLanguage());
        values.put(MovieDB.COLUMN_NAME_COUNTRY, movie.getCountry());
        values.put(MovieDB.COLUMN_NAME_AWARDS, movie.getAwards());
        values.put(MovieDB.COLUMN_NAME_POSTER_URL, movie.getPoster());
        values.put(MovieDB.COLUMN_NAME_METASCORE, movie.getMetascore());
        values.put(MovieDB.COLUMN_NAME_IMDB_ID, movie.getImdbID());
        values.put(MovieDB.COLUMN_NAME_IMDB_RATING, movie.getImdbRating());
        values.put(MovieDB.COLUMN_NAME_IMDB_VOTES, movie.getImdbVotes());
        values.put(MovieDB.COLUMN_NAME_TYPE, movie.getType());

        // Insere o filme
        getDatabase().insert(MovieDB.TABLE_NAME, null, values);
    }

    public List<Movie> loadMovies() {
        List<Movie> movieList = new ArrayList<>();

        String[] projection = {
                MovieDB._ID,
                MovieDB.COLUMN_NAME_TITLE,
                MovieDB.COLUMN_NAME_YEAR,
                MovieDB.COLUMN_NAME_RATED,
                MovieDB.COLUMN_NAME_RELEASED,
                MovieDB.COLUMN_NAME_RUNTIME,
                MovieDB.COLUMN_NAME_GENRE,
                MovieDB.COLUMN_NAME_DIRECTOR,
                MovieDB.COLUMN_NAME_WRITER,
                MovieDB.COLUMN_NAME_ACTORS,
                MovieDB.COLUMN_NAME_PLOT,
                MovieDB.COLUMN_NAME_LANGUAGE,
                MovieDB.COLUMN_NAME_COUNTRY,
                MovieDB.COLUMN_NAME_AWARDS,
                MovieDB.COLUMN_NAME_POSTER_URL,
                MovieDB.COLUMN_NAME_METASCORE,
                MovieDB.COLUMN_NAME_IMDB_RATING,
                MovieDB.COLUMN_NAME_IMDB_VOTES,
                MovieDB.COLUMN_NAME_IMDB_ID,
                MovieDB.COLUMN_NAME_TYPE
        };

        Cursor cursor = getDatabase().query(
                MovieDB.TABLE_NAME,       // Nome da table
                projection,               // Colunas a retornar
                null,                     // Colunas da clausula WHERE
                null,                     // Valores da clausula WHERE
                null,                     // Agrupamento das linhas
                null,                     // Filtros das linhas
                null                      // Ordenacao
                );

        while (cursor.moveToNext()) {
            // Cria o filme com os valores do BD
            Movie movie = new Movie();
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_TITLE)));
            movie.setYear(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_YEAR)));
            movie.setRated(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_RATED)));
            movie.setReleased(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_RELEASED)));
            movie.setRuntime(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_RUNTIME)));
            movie.setGenre(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_GENRE)));
            movie.setDirector(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_DIRECTOR)));
            movie.setWriter(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_WRITER)));
            movie.setActors(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_ACTORS)));
            movie.setPlot(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_PLOT)));
            movie.setLanguage(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_LANGUAGE)));
            movie.setCountry(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_COUNTRY)));
            movie.setAwards(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_AWARDS)));
            movie.setPoster(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_POSTER_URL)));
            movie.setMetascore(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_METASCORE)));
            movie.setImdbRating(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_IMDB_RATING)));
            movie.setImdbVotes(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_IMDB_VOTES)));
            movie.setImdbID(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_IMDB_ID)));
            movie.setType(cursor.getString(cursor.getColumnIndex(MovieDB.COLUMN_NAME_TYPE)));

            // Adiciona o filme na lista de retorno
            movieList.add(movie);
        }

        // Fecha e libera o cursor
        cursor.close();

        return movieList;
    }

    public void deleteMovie(Movie movie) {
        // Define a clausula WHERE
        String selection = MovieDB.COLUMN_NAME_TITLE + " = ?";
        // Define o argumento do WHERE
        String[] selectionArgs = { movie.getTitle() };
        // Remove o filme do BD
        getDatabase().delete(MovieDB.TABLE_NAME, selection, selectionArgs);
    }
}
