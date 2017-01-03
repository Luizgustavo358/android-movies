package com.rafaelguimas.movies.db;

import android.provider.BaseColumns;

/**
 * Created by Rafael on 30/12/2016.
 */

public class MovieDB implements BaseColumns {

    // Colunas da tabela
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_YEAR = "year";
    public static final String COLUMN_NAME_RATED = "rated";
    public static final String COLUMN_NAME_RELEASED = "released";
    public static final String COLUMN_NAME_RUNTIME = "runtime";
    public static final String COLUMN_NAME_GENRE = "genre";
    public static final String COLUMN_NAME_DIRECTOR = "director";
    public static final String COLUMN_NAME_WRITER = "writer";
    public static final String COLUMN_NAME_ACTORS = "actors";
    public static final String COLUMN_NAME_PLOT = "plot";
    public static final String COLUMN_NAME_LANGUAGE = "language";
    public static final String COLUMN_NAME_COUNTRY = "country";
    public static final String COLUMN_NAME_AWARDS = "awards";
    public static final String COLUMN_NAME_POSTER_URL = "poster_url";
    public static final String COLUMN_NAME_METASCORE = "metascore";
    public static final String COLUMN_NAME_IMDB_RATING = "imdb_rating";
    public static final String COLUMN_NAME_IMDB_VOTES = "imdb_votes";
    public static final String COLUMN_NAME_IMDB_ID = "imdb_id";
    public static final String COLUMN_NAME_TYPE = "type";

    // Operacoes
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RATED + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RELEASED + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_RUNTIME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DIRECTOR + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_WRITER + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_ACTORS + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PLOT  + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_AWARDS + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_POSTER_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_METASCORE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_IMDB_RATING + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_IMDB_VOTES + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_IMDB_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_TYPE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Contrutor vazio
    public MovieDB() {}
}
